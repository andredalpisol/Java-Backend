package com.soulcode.Servicos.Util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UploadFile {

    // FUNÇÃO SURGE QUANDO TEMOS INPUT TIPO FILE PARA ADICIONARMOS ARQUIVOS
// PRIMEIRO PARAMETRO É O CAMINHO DO ARQUIVO - SEGUNDO É O NOME DO ARQUIVO (PASSAMOS PARA MUDAR O NUME DO ARQUIVO DIFERENTE DO QUE ESTA SALVO NO TEU COMPUTADOR)
// TERCEIRO É O ARQUIVO EM SI .JPG - .PNG

    public static void saveFile (String uploadDir, String fileName, MultipartFile file) throws IOException {

        //AQUI O UPLOADPATH PEGA O PARAMETRO INFORMADO NO UPLOADDIR E SALVA NO UPLOADPATH
        Path uploadPath = Paths.get(uploadDir);

        //CONDIÇÃO PARA VERMOS SE O DIRETORIO EXISTE - SE O CAMINHO INFORMADO REALMENTE EXISTE, SE NÃO EXISTIR O DIRETORIO SERA CRIADO DO ZERO
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }


        // AQUI VAMOS TENTAR FAZER O UPLOAD DO ARQUIVO -
        // INPUTSTREAM VAI FAZER LEITURA BYTE/BYTE DO ARQUIVO QUE ESTAMOS QUERENDO SUBIR ATRAVES DO INPUT
        try(InputStream inputStream = file.getInputStream()){
            //se a leitura foi feita de forma correta, o arquivo é salvo no diretorio que foi passado na assinatura do método
            Path filePath = uploadPath.resolve(fileName);
            //nesse momento iremos fazer a cópia do arquivo do hd para o diretorio de upload
            //passamos de parametro: o arquivo já lido, o caminho do arquivo e também qual tipo de cópia que estamos fazendo (nesse caso se ja tiver copia no diretorio vai substituir)
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        // aqui ocorrerá se der algum erro
        catch (IOException e){
            throw new IOException("Não foi possivel fazer o upload do seu arquivo");
        }
    }
}
