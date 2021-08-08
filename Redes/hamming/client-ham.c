#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <netdb.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdbool.h>
#include "canal.c"
#define MAXDATASIZE 512 // max number of bytes we can get at once 
#define PORT 5555


int main()
{
    struct hostent *host_entity;
    struct sockaddr_in serv_addr;
    char input_client[MAXDATASIZE], input_x_client[MAXDATASIZE], client_hcode[2*MAXDATASIZE], client_bytestuff[2*MAXDATASIZE], client_encriptada[2*MAXDATASIZE], serv_bytedestuff[2*MAXDATASIZE], serv_hdecode[2+MAXDATASIZE];
    int sock, maxfd, addrlen = sizeof(serv_addr), numbytes;
    fd_set master, read_fds;

    serv_addr.sin_family = AF_INET; 
    serv_addr.sin_port = htons(PORT);
    host_entity = gethostbyname("localhost");
    bcopy((char*)host_entity -> h_addr, (char*)&serv_addr.sin_addr.s_addr, host_entity ->h_length);
    sock = socket(AF_INET, SOCK_STREAM, 0);
    
    connect(sock, (struct sockaddr*)&serv_addr, sizeof(serv_addr));

    FD_ZERO(&master);
    FD_SET(sock, &master);
    FD_SET(0, &master); // 0 for keyboard
    maxfd = sock;

    while(true) // client loop
    {
        read_fds = master;
        select(maxfd + 1, &read_fds, NULL, NULL, NULL);
        if (FD_ISSET(0, &read_fds))
        {
            fgets(input_client, MAXDATASIZE, stdin);

            hcode(input_client, client_hcode);
            bytestuff(client_hcode, client_bytestuff);
            canal(client_bytestuff, client_encriptada);

            send(sock, client_encriptada, strlen(client_encriptada) + 1, 0);
        } else {
            numbytes = recv(sock, serv_bytedestuff, MAXDATASIZE, 0);

            bytedestuff(serv_bytedestuff, serv_hdecode);
            hdecode(serv_hdecode, input_x_client);

            input_x_client[numbytes/2] = 0;
            puts(input_x_client);
            // close(socket); can't close socket here
        }
        
    }
	return 0;
}
