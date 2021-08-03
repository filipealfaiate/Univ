#include <stdio.h>

int fib(int a)
{
	if (a == 0) return 0;
	if (a == 1) return 1;

	return fib(a-1) + fib(a-2);
}

int main()
{
	int num;

	scanf("%d", &num);
	printf("%d\n", fib(num));

	return 0;
}