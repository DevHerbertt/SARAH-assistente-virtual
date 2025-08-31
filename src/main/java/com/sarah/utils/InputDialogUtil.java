package com.sarah.utils;

import javax.swing.*;
import java.awt.*;

public class InputDialogUtil {

    public static String mostrarDialogo(String titulo) {
        final String[] mensagem = new String[1];
        final Object lock = new Object();  // Objeto para sincronizar

        // Cria um SwingWorker para não bloquear a thread principal
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Cria um diálogo modal (bloqueia até o usuário interagir)
                JDialog dialog = new JDialog((Frame) null, titulo, true);
                dialog.setLayout(new BorderLayout());
                dialog.setSize(400, 100);
                dialog.setLocationRelativeTo(null); // Centraliza na tela

                JTextField campoTexto = new JTextField();
                JButton botaoEnviar = new JButton("Enviar");

                // Quando o botão é clicado
                botaoEnviar.addActionListener(e -> {
                    mensagem[0] = campoTexto.getText();
                    dialog.dispose();  // Fecha o diálogo
                });

                dialog.add(campoTexto, BorderLayout.CENTER);
                dialog.add(botaoEnviar, BorderLayout.EAST);

                dialog.setVisible(true);
                return null;
            }

            @Override
            protected void done() {
                // Notifica a thread principal quando o processo terminar
                synchronized (lock) {
                    lock.notify();
                }
            }
        };

        // Inicia o SwingWorker
        worker.execute();

        // Espera até o usuário preencher o campo e clicar no botão
        synchronized (lock) {
            try {
                lock.wait();  // Aguarda a notificação do SwingWorker
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return mensagem[0];  // Retorna a mensagem digitada
    }

    public static String execute(String name) {
        // Teste o método mostrando a janela de entrada
        String resultado = mostrarDialogo("Digite o nome da pasta");
        System.out.println("Nome da pasta: " + resultado);
        return resultado;
    }
}
