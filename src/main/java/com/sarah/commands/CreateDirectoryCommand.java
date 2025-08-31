//package com.sarah.commands;
//
//import com.sarah.service.DirectoryService;
//import com.sarah.utils.QuestionAndResponderUtil;
//import com.sarah.voice.VoiceResponderDefault;
//
//public class CreateDirectoryCommand implements Command{
//    private final DirectoryService directoryService;
//    private final QuestionAndResponderUtil questionAndResponderUtil;
//
//    public CreateDirectoryCommand(DirectoryService directoryService, QuestionAndResponderUtil questionAndResponderUtil) {
//        this.directoryService = directoryService;
//        this.questionAndResponderUtil = questionAndResponderUtil;
//    }
//
//    @Override
//    public void execute() {
//        String directoryName = questionAndResponderUtil.askAndListen(VoiceResponderDefault.NOME_DIRETORIO);
//        return directoryService.createdDirectory(directoryName);
//    }
//}
