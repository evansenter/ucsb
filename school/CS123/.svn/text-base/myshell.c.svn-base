/* $begin shellmain */
#include "csapp.h"
#define MAXARGS   128

/* function prototypes */
void eval(char*cmdline, char *alias_list, char *env[]);
int parseline(char *buf, char **argv, char *alias_list, char *env[]);
int builtin_command(char **argv); 

int main(int argc, char *argv[], char *env[]) 
{
    char cmdline[MAXLINE]; 	/* command line */
		char hostname[MAXLINE];						
		char alias_list[1024];						
		int i = -1;
		while (env[++i]) printf("%s\n", env[i]);
		gethostname(hostname, MAXLINE);		

    while (1) {
	/* read */
	printf("%s> ", hostname);       		   
	Fgets(cmdline, MAXLINE, stdin); 
	if (feof(stdin))
	    exit(0);

	/* evaluate */
	eval(cmdline, alias_list, env);
    } 
}
/* $end shellmain */
  
/* $begin eval */
/* eval - evaluate a command line */
void eval(char *cmdline, char *alias_list, char *env[]) 
{
    char *argv[MAXARGS]; /* argv for execve() */
    char buf[MAXLINE];   /* holds modified command line */
    int bg;              /* should the job run in bg or fg? */
    pid_t pid;           /* process id */
    
    strcpy(buf, cmdline);
    bg = parseline(buf, argv, alias_list, env); 
    if (argv[0] == NULL)  
	return;   /* ignore empty lines */

    if (!builtin_command(argv)) { 
	if ((pid = Fork()) == 0) {   /* child runs user job */
	    if (execve(argv[0], argv, environ) < 0) {
		printf("%s: Command not found.\n", argv[0]);
		exit(0);
	    }
	}

	/* parent waits for foreground job to terminate */
	if (!bg) {
	    int status;
	    if (waitpid(pid, &status, 0) < 0)
		unix_error("waitfg: waitpid error");
	}
	else
	    printf("%d %s", pid, cmdline);
    }
    return;
}

/* if first arg is a builtin command, run it and return true */
int builtin_command(char **argv) 
{
    if (!strcmp(argv[0], "quit")) /* quit command */
	exit(0);  
    if (!strcmp(argv[0], "&"))    /* ignore singleton & */
	return 1;
    return 0;                     /* not a builtin command */
}
/* $end eval */

/* $begin parseline */
/* parseline - parse the command line and build the argv array */
int parseline(char *buf, char **argv, char *alias_list, char *env[])
{
    char *delim;         /* points to first space delimiter */
    int argc;            /* number of args */
    int bg;              /* background job? */

    buf[strlen(buf)-1] = ' ';  /* replace trailing '\n' with space */
    while (*buf && (*buf == ' ')) /* ignore leading spaces */
	buf++;

		if (strncmp("alias", buf, 5) == 0)
			if (!strstr(alias_list, buf))
				alias_list = strcat(strcat(alias_list, "#"), buf);
		
    /* build the argv list */
    argc = 0;
    while ((delim = strchr(buf, ' '))) {
	argv[argc++] = buf;
	*delim = '\0';
	buf = delim + 1;
	while (*buf && (*buf == ' ')) /* ignore spaces */
	       buf++;
    }
    argv[argc] = NULL;


		if (strncmp("setenv", buf, 6) == 0) {
			int i = -1;
			while (env[++i])
			  if (strstr(env[i], argv[1]))
				env[i] = strcat(strcat(argv[1], "="), argv[2]);
		}
		/*int i = -1;
		while (env[++i]) printf("%s\n", env[i]);
		*/
		char *position = NULL;
   	if (!strstr(argv[0], "alias") && (position = strstr(alias_list, argv[0])))
			argv[0] = strtok(strstr(position, " "), "#");
				
		printf("- Substituted command: %s\n- Alias list: %s\n", argv[0], alias_list);

    if (argc == 0)  /* ignore blank line */
	return 1;

    /* should the job run in the background? */
    if ((bg = (*argv[argc-1] == '&')) != 0)
	argv[--argc] = NULL;

    return bg;
}
/* $end parseline */

