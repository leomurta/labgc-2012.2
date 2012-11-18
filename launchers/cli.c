#include <stdio.h>
#include <string.h>
#include <limits.h>
#include <unistd.h>
#include <stdlib.h>

typedef struct cmdbase {
    char *jar_path;
    char *rmi_path;
    char *rmi_host;
    char *policity_path;
    char *repository_path;
} TBase;


TBase * read_file() {
    FILE *p = NULL;
    char type[120];
    char path[120];
    char ref[120];
    char enter[2];

    if (!(p = fopen("config.ini", "r")))
        return;

    TBase * base = (TBase*) malloc(sizeof (TBase));

    base->jar_path = NULL;
    base->policity_path = NULL;
    base->rmi_host = NULL;
    base->rmi_path = NULL;
    base->repository_path = NULL;

    while (!feof(p)) {
        fscanf(p, "%[^=]=%[^\n]s", type, path);

        if (!strcmp("REF", type)) {
            realpath(path, ref);
            int i;
            for (i = 0; ref[i] != '\0'; i++);
            ref[i++] = '/';
            ref[i] = '\0';

        }
        if (!strcmp("JAR_PATH", type)) {
            base->jar_path = (char*) malloc(PATH_MAX);
            sprintf(base->jar_path, "%s%s", ref, path);
        }
        if (!strcmp("RMI_CODEBASE", type)) {
            base->rmi_path = (char*) malloc(PATH_MAX);
            sprintf(base->rmi_path, "%s%s", ref, path);
        }
        if (!strcmp("RMI_HOSTNAME", type)) {
            base->rmi_host = (char*) malloc(PATH_MAX);
            strcpy(base->rmi_host, path);
        }
        if (!strcmp("POLICITY", type)) {
            base->policity_path = (char*) malloc(PATH_MAX);
            sprintf(base->policity_path, "%s%s", ref, path);
        }
        if (!strcmp("REPOSITORY", type)) {
            base->repository_path = (char*) malloc(PATH_MAX);
            sprintf(base->repository_path, "%s%s", ref, path);
        }
        fscanf(p, "%[\n]", enter);
    }
    fclose(p);

    return base;
}

void freeall(TBase * base) {
    free(base->jar_path);
    free(base->policity_path);
    free(base->repository_path);
    free(base->rmi_path);
    free(base->rmi_host);
}

void server(int argc, char **argv,char *abs_exe_path){
    TBase * base = read_file();
    char arg[PATH_MAX] = "";
    
    if (argc > 0) {
        int i;
        strcpy(base->rmi_host,argv[1]);
        for (i = 2; i < argc; i++) {
            strcat(arg, argv[i]);
            strcat(arg, " ");
        }
    }
    
    int n = sprintf(abs_exe_path, "java -cp %s -Dbr.uff.ic.labgc.mode=server -Dbr.uff.ic.labgc.repository=%s/ -Djava.rmi.server.codebase=file:%s/ -Djava.rmi.server.hostname=%s -Djava.security.policy=%s br.uff.ic.labgc.AppCLI %s", base->jar_path, base->repository_path, base->rmi_path, base->rmi_host, base->policity_path, arg);
    freeall(base);
    free(base);
}

void client(int argc, char **argv,char *abs_exe_path,char *path_invocation){
    TBase * base = read_file();
    char arg[PATH_MAX] = "";
    
    if (argc > 0) {
        int i;
        for (i = 1; i < argc; i++) {
            strcat(arg, argv[i]);
            strcat(arg, " ");
        }
    }
    int n = sprintf(abs_exe_path, "java -cp %s -Dbr.uff.ic.labgc.invocation=%s/ -Dbr.uff.ic.labgc.repository=%s/ -Djava.rmi.server.codebase=file:%s/ -Djava.rmi.server.hostname=%s -Djava.security.policy=%s br.uff.ic.labgc.AppCLI %s", base->jar_path, path_invocation, base->repository_path, base->rmi_path, base->rmi_host, base->policity_path, arg);
    freeall(base);
    free(base);
}

int main(int argc, char **argv) {
    char path_invocation[PATH_MAX];
    char abs_exe_path[PATH_MAX];
    

    char *p;

    if (!(p = strrchr(argv[0], '/'))) {
        getcwd(abs_exe_path, sizeof (abs_exe_path));
        *path_invocation = *abs_exe_path;
    } else {
        *p = '\0';
        getcwd(path_invocation, sizeof (path_invocation));
        chdir(argv[0]);       
        getcwd(abs_exe_path, sizeof (abs_exe_path));       
    }
    
    client(argc,argv,abs_exe_path,path_invocation);
    
    //server(argc,argv,abs_exe_path);
    
    printf("%s\nRunning...\n", abs_exe_path,path_invocation);
    system(abs_exe_path);
    
    return 0;
}
