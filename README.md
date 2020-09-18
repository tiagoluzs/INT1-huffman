# INT1-huffman

Grupo:

Ana Luiza de Azambuja Mattos

Igor de Souza Couto

Luis Augusto Alves de Lima

Tiago Luz da Silva




Para compilar:


~# cd huffmann


~# mvn clean package -Dcom.mycompany.huffmann.App


Para rodar testes unitários:


~# cd huffmann


~# mvn test




Para compactar:

~# cd target

~# java -cp huffmann-1.0-SNAPSHOT.jar com.mycompany.huffmann.App <NOME_DO_ARQUIVO> compactar




Para descompactar:

~# cd target

~# java -cp huffmann-1.0-SNAPSHOT.jar com.mycompany.huffmann.App <NOME_DO_ARQUIVO> descompactar




Para exibir tabela de frequencia:

~# cd target

~# java -cp huffmann-1.0-SNAPSHOT.jar com.mycompany.huffmann.App <NOME_DO_ARQUIVO> tabela





Enunciado:



1) Embora o artigo-exemplo se proponha a tratar do problema de compactação de dados em
arquivos texto, observe que tanto o código-fonte em Java quanto o de Haskell não incluem
a implementação de todas as etapas necessárias. Escolha Java ou Haskell e implemente:
a) O cálculo de frequência da tabela de símbolos.
b) O código necessário capaz de realizar a compactação e descompactação de um
arquivo texto. Dica: antes de efetuar a implementação dos módulos adicionais,
pesquise nas referências bibliográficas as diferentes opções de implementação do
processo de compactação e descompactação.
