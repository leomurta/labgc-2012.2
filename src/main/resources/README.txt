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
4. O cliente deve ser invocado de maneira semelhante:
   java -cp c:\app\classes;c:\app\target\evcs.jar
        -Djava.rmi.server.codebase=file:/c:/app/target/evcs.jar
        -Djava.rmi.server.hostname=localhost
        -Djava.security.policy=evcs.policy
           br.uff.ic.labgc.App

Exemplo servidor:
java -cp labgc-2012.2.jar -Dbr.uff.ic.labgc.mode=server -Djava.rmi.server.codebase=file:/F:/mybackups/Educacao/Mestrado-UFF/Git/labgc-2012.2/target/classes/ -Djava.rmi.server.hostname=localhost -Djava.security.policy=F:\mybackups\Educacao\Mestrado-UFF\Git\labgc-2012.2\target\classes\evcs.policy br.uff.ic.labgc.App

Exemplo cliente:
java -cp labgc-2012.2.jar -Djava.rmi.server.codebase=file:/F:/mybackups/Educacao/Mestrado-UFF/Git/labgc-2012.2/target/classes/ -Djava.rmi.server.hostname=localhost -Djava.security.policy=F:\mybackups\Educacao\Mestrado-UFF\Git\labgc-2012.2\target\classes\evcs.policy br.uff.ic.labgc.App