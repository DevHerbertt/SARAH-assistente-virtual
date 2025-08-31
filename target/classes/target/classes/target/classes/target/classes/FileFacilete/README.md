# FileFacilite

## Descrição

O projeto **FileFacilite** foi desenvolvido com o objetivo de facilitar a manipulação de arquivos e diretórios, garantindo maior escalabilidade e eficiência. Além disso, este projeto serviu como um desafio pessoal para melhorar minhas habilidades em desenvolvimento backend com Java.

## Tecnologias Utilizadas

- **Java 22**
- **API NIO (java.nio.file)**

## Estrutura de Arquivos

A organização do projeto segue uma estrutura clara e modular para facilitar a navegação e manutenção:

```
FileFacilite/
├── src/main/java/
│   ├── br/com/filefacilete/controllers/    # Controladores responsáveis por gerenciar as interações
│   ├── br/com/filefacilete/core/           # Classe principal para inicialização do aplicativo
│   ├── br/com/filefacilete/models/         # Classes de modelos (dados e entidades)
│   ├── br/com/filefacilete/services/       # Regras de negócio e operações sobre arquivos/diretórios
│   ├── br/com/filefacilete/utils/          # Funções utilitárias reutilizáveis
│   └── br/com/filefacilete/view/           # Interface do usuário (se aplicável)
├── src/main/test/java/                     # Testes unitários
├── pom.xml                                 # Arquivo de configuração do Maven
├── .gitignore                              # Arquivos a serem ignorados no controle de versão
├── README.md                               # Documentação do projeto
```
## Como Funciona
O programa oferece um menu inicial com 8 interações principais, permitindo:  
1. Criar diretórios.  
2. Criar arquivos.  
3. Excluir diretórios ou arquivos.  
4. Verificar atributos de arquivos/diretórios.  
5. Ler e exibir todos os seus arquivos.  

Os arquivos manipulados pelo sistema são organizados e armazenados em uma pasta específica, previamente configurada para o programa.  

:bangbang: **Nota Importante:**  
O **FileFacilete** foi projetado apenas para fins educacionais e pequenos projetos. Ele **não deve ser usado em ambientes profissionais ou empresariais**.  

## Como Usar

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/DevHerbertt/FileFacilite.git
   ```

2. **Navegue até o diretório do projeto:**
   ```bash
   cd FileFacilite
   ```

3. **Compile e execute o projeto:**
   Certifique-se de ter o Java 22 instalado e configurado corretamente.
   ```bash
   javac -d bin src/main/java/br/com/filefacilite/core/FileFaciliteApp.java
   java -cp bin br.com.filefacilite.core.FileFaciliteApp
   ```
   

## Como Contribuir

Contribuições são bem-vindas! Siga estas etapas:

1. Faça um fork do projeto.
2. Crie uma nova branch para suas alterações:
   ```bash
   git checkout -b minha-branch
   ```
3. Commit suas alterações:
   ```bash
   git commit -m "Descrição das alterações"
   ```
4. Faça o push para a branch:
   ```bash
   git push origin minha-branch
   ```
5. Abra um pull request no repositório original.

## Autor

- **Herbert** - Desenvolvedor Backend em formação, com foco em projetos escaláveis e soluções eficientes.
