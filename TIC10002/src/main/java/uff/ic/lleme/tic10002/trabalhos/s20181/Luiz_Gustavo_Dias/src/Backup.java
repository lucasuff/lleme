
public class Backup {

    private static int qtdAtendimentos;
    private static Atendimento primeiro;
    private static Atendimento ultimo;

    public static int getQtdAtendimentos() {
        return qtdAtendimentos;
    }

    public static void setQtdAtendimentos(int qtdAtendimentos) {
        Backup.qtdAtendimentos = qtdAtendimentos;
    }

    public static Atendimento getPrimeiro() {
        return primeiro;
    }

    public static void setPrimeiro(Atendimento primeiro) {
        Backup.primeiro = primeiro;
    }

    public static Atendimento getUltimo() {
        return ultimo;
    }

    public static void setUltimo(Atendimento ultimo) {
        Backup.ultimo = ultimo;
    }

    public void insereBackup(Backup fila, Atendimento backup) {
        if (fila.qtdAtendimentos == 0) {
            Backup.primeiro = backup;
            Backup.ultimo = backup;

        } else {
            getPrimeiro().proximo = backup;
            setUltimo(backup);

        }

        setQtdAtendimentos(getQtdAtendimentos() + 1);
    }

    public static void mostraHistorico() {
        System.out.println("\ncomando: **Ver Hist�rico**\nAtendimentos do dia: " + getQtdAtendimentos() + ":");
        for (int i = 0; i < qtdAtendimentos; i++) {
            System.out.println("\nCliente:" + primeiro.cliente.nome);
            primeiro = primeiro.proximo;
        }

    }
}
