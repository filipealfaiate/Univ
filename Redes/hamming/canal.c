#include <stdio.h>
#include <math.h>
#include <string.h>
#define CARACTERE 100

void canal( char* in, char* out)
{
	int i = 1, erro = 0;
	while (*in != '\0')
	{
        erro = 0;      
		i = (i + 5)%7;

		if(i > 3)
			erro = 0;

		else
			erro = pow(2, i);

		*out = (*in)^(char)(erro);
		out++;
		in++;
		i++;
	}
	*out = '\0';
}	

void hcode(char *input_string, char *output_string)
{
	char aux1, aux2, cb1, cb2, cb3, cb4, cb5, cb6;
	int j = 0;

	for(int i = 0; input_string[i] != '\0'; i++, j+=2)
	{
		aux1 = input_string[i] >> 4;
		aux2 = input_string[i] & 0b00001111;

		cb1 = (((aux1 & 0b1000) >> 3) ^ ((aux1 & 0b0100) >> 2) ^ (aux1 & 0b0001)) << 6;
		cb2 = (((aux1 & 0b1000) >> 3) ^ ((aux1 & 0b0010) >> 1) ^ (aux1 & 0b0001)) << 5;
		cb3 = (((aux1 & 0b0100) >> 2) ^ ((aux1 & 0b0010) >> 1) ^ (aux1 & 0b0001)) << 4;
		output_string[j] = cb1 | cb2 | cb3 | aux1 | 0b10000000;

		cb4 = (((aux2 & 0b1000) >> 3) ^ ((aux2 & 0b0100) >> 2) ^ (aux2 & 0b0001)) << 6;
		cb5 = (((aux2 & 0b1000) >> 3) ^ ((aux2 & 0b0010) >> 1) ^ (aux2 & 0b0001)) << 5;
		cb6 = (((aux2 & 0b0100) >> 2) ^ ((aux2 & 0b0010) >> 1) ^ (aux2 & 0b0001)) << 4;
		output_string[j+1] = cb4 | cb5 | cb6 | aux2 | 0b10000000;
	}
	output_string[j] = '\0';
}

void bytestuff(char *input_string, char *output_string)
{
	int i = 0, j = 0;
	while(input_string[i] != '\0')
	{
		output_string[j] = input_string[i];
		if (input_string[i] == 'H')
		{
			j++;
			output_string[j] = '0';
		}		
		i++; j++;
	}
	output_string[j] = '\0';
}

void bytedestuff(char *input_string, char *output_string)
{
	int i = 0, j = 0;
	while(input_string[i] != '\0')
	{
		output_string[j] = input_string[i];
		if (input_string[i] == 'H')
			i++;
		j++; i++;
	}	
}

char verifica (char input_string)
{
	char cb1, cb2, cb3, cb4, cb5, cb6, cb7, fcheck = 0b00000000;

	cb1 = (input_string & 0b01000000) >> 6;
	cb2 = (input_string & 0b00100000) >> 5;
	cb3 = (input_string & 0b00010000) >> 4;
	cb4 = (input_string & 0b00001000) >> 3;
	cb5 = (input_string & 0b00000100) >> 2;
	cb6 = (input_string & 0b00000010) >> 1;
	cb7 = input_string & 0b00000001;

	cb1 = cb1 ^ cb4 ^ cb5 ^ cb7;
	cb2 = cb2 ^ cb4 ^ cb6 ^ cb7;
	cb3 = cb3 ^ cb5 ^ cb6 ^ cb7;

	fcheck = (cb3 << 2) | (cb2 << 1) | cb1;

	switch(fcheck)
	{
		case 3:
			input_string = input_string ^ 0b00001000;
			break;
		case 5:
			input_string = input_string ^ 0b00000100;
			break;
		case 6:
			input_string = input_string ^ 0b00000010;
			break;
		case 7:
			input_string = input_string ^ 0b00000001;
	}
	return input_string;
}

void hdecode(char *input_string, char *output_string)
{
	int j = 0;
	char aux1, aux2;

	for (int i = 0; input_string[i] != '\0'; i += 2, ++j)
	{
		
		aux1 = (verifica(input_string[i])) << 4;

		aux2 = (verifica(input_string[i+1])) & 0b0001111;

		output_string[j] = aux1 | aux2;
	}
	output_string[j] = '\0';
}