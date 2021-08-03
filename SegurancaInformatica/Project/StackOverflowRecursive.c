#include <stdio.h>

void func(int a, int b, int c, int d)
{
	printf("%d\n", a);
	
	char e[94] = "Com mais variavais, origina Segmentation Fault mais cedo do que apenas com uma unica variavel";

	func(a+1, b, c, d);
}

int main()
{
	func(0, 0, 0, 0);

	return 0;
}