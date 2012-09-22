Passos para a inicialização do servidor:
1. Inicializar o rmiregistry (Ex.: start rmiregistry)
2. Possuir um arquivo de policy estabelecendo as permissões do servidor. Ex.:
    grant codeBase "file:/c:/app/classes" {
        permission java.security.AllPermission;
    };
    O arquivo acima pode ser salvo como evcs.policy
3. Supondo que o servidor seja salvo no arquivo evcs.jar, invocá-lo da seguinte forma:
   java -cp c:\app\classes;c:\app\target\evcs.jar
        -Djava.rmi.server.codebase=file:/c:/app/target/evcs.jar
        -Djava.rmi.server.hostname=localhost
        -Djava.security.policy=evcs.policy
           br.uff.ic.labgc.comm.server.CommunicationServer