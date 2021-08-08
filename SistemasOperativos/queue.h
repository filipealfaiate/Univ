typedef struct queue queue_t;
queue_t *queue_new(int size);
void enqueue(queue_t *queue_v, int elemento);
int dequeue(queue_t *queue_v);
bool empty(queue_t *queue_v);
int top(queue_t *queue_v);
bool full(queue_t *queue_v);
void printAll(queue_t *queue_v);
int posicao(queue_t *queue_v, int pos);