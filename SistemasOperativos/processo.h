typedef struct processo processo_t;
processo_t *processo_new(int size);
processo_t *input_processo(int arr[], int ini, int fim);
int posicao_PID(int PID, processo_t *processo_v[], int n_processo);
void atualizar_processo_blocked(int n_processo, processo_t *processo_v[], int size);
void atualizar_processo_run(int n_processo, processo_t *processo_v[], int size);