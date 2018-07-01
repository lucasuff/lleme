using System;
using System.Collections.Generic;
using System.Linq;
using System.Text; 
using System.Threading.Tasks;

namespace Atendimento // nome do programa
{
    class Program // classe do programa chamado Program 
    {
        public static List<Atendimento> Atendimentos = new List<Atendimento>(); // foi criada uma lista de objetos Atendimento (ver embaixo os objetos que são Atendimento). Nome da lista criada (vazia) é Atendimentos
        public static List<TipoAssunto> TiposAssunto = new List<TipoAssunto>(); // criada uma lista de objetos TipoAssunto chamada TiposAssunto.

        static void Main(string[] args) 
        {
            CarregarDadosTeste(); // função que foi criada, depois vai escrever o bem vindo e vai rodar o menu principal ,que já chama ele na sequencia
            Console.WriteLine($"--Bem vindo--"); // função para acessar as infos relacionadas ao console. Podia ser só write também
            MenuPrincipal();
        }

        private static void MenuPrincipal()
        {
            Console.Clear(); // toda vez ele vai limpar o console
            Console.WriteLine($"Digite o tipo de ação a realizar: \n 1. Recepcionar \n 2. Atender \n 3. Encerrar \n 4. Gerar Estatística \n 0. Sair");
            int opcaoEscohida = 0; // para guardar o valor que vai ser digitado
            if(int.TryParse(Console.ReadLine(), out opcaoEscohida)) //inteiro le e joga pra fora a opção escolhida. Atrivui o valor da opção escolhida. Vai digitar, e da o switch q tem embaixo
            {
                switch (opcaoEscohida)
                {
                    case 1:
                        Recepcionar();
                        break;
                    case 2:
                        Atender();
                        break;
                    case 3:
                        Encerrar();
                        break;
                    case 4:
                        GerarEstatistica();
                        break;
                    case 0:
                        System.Environment.Exit(0);
                        break;
                    default: // caso digite alguma coisa que não seja nenhum destes outros valores
                        Console.WriteLine("Opção escolhida inválida");
                        break;
                }
            }

            MenuPrincipal(); // volta pro menu principal após fazer qualquer uma destas ações acima
        }

        private static void Atender()
        {
            if (!Atendimentos.Any(a => a.StatusAtendimento == StatusAtendimentoEnum.AguardandoAtendimento)) // se a lista de atendimento tiver alguém, existe gente pra ser atendida, pessoas com status diferentes
            {
                Console.WriteLine("Não há clientes aguardando atendimento");
                Console.ReadKey(); // é para "ver" o resultado na tela do console, ele vai "ler" uma tecla. Vai esperar q seja informado pra partir pras linhas seguintes
                return;
            }
            IEnumerable<Atendimento> atendimentosAguardando = NewMethod();
            var maiorPrioridadeIdentificada = atendimentosAguardando.Max(a => a.Cliente.Idade + TimeSpan.FromMilliseconds(a.HoraChegada.Millisecond).Minutes + a.Assuntos.Sum(b => b.TipoAssunto.GrauUrgencia)); // quem tem maior grau de urgencia
            var atendimento = Atendimentos.FirstOrDefault(a => a.Cliente.Idade + TimeSpan.FromMilliseconds(a.HoraChegada.Millisecond).Minutes + a.Assuntos.Sum(b => b.TipoAssunto.GrauUrgencia) == maiorPrioridadeIdentificada); // quem tem maior prioridade
            atendimento.StatusAtendimento = StatusAtendimentoEnum.EmAtendimento;
            atendimento.HoraAtendimento = DateTime.Now;
            Console.Clear();
            Console.WriteLine("-- Cliente selecionado para atendimento --\n");

            ExibirAtendimento(atendimento);
            return;
        }

        private static IEnumerable<Atendimento> NewMethod()
        {
            return Atendimentos.Where(at => at.StatusAtendimento == StatusAtendimentoEnum.AguardandoAtendimento);
        }

        private static void GerarEstatistica()
        {
            var atendimentosEstatistica = Atendimentos.Where(a => a.StatusAtendimento == StatusAtendimentoEnum.Encerrado 
                                                               && a.HoraChegada.Day == DateTime.Now.Day 
                                                               && a.HoraChegada.Month == DateTime.Now.Month 
                                                               && a.HoraChegada.Year == DateTime.Now.Year).ToList();
            var assuntos = atendimentosEstatistica.SelectMany(a => a.Assuntos);

            var groupTipoAssunto = assuntos.GroupBy(a => a.TipoAssuntoId).ToList();

            for (int i = 0; i < groupTipoAssunto.Count(); i++)
            {
                var grupo = groupTipoAssunto[i];
                var media = grupo.Select(a => a.DuracaoAtendimento).Average(b=> b.Minutes);
                Console.WriteLine($"Assunto: {grupo.First().TipoAssunto.Descricao} - Tempo médio de atendimento: {media} minutos");
            }
            Console.ReadKey();
        }

        private static void Encerrar()
        {
            if (!Atendimentos.Any(a => a.StatusAtendimento == StatusAtendimentoEnum.EmAtendimento))
            {
                Console.WriteLine($"Não há clientes em atendimento");
                Console.ReadKey();
                MenuPrincipal();
                return;
            }

            Console.WriteLine($"-- Listagem de clientes em atendimento --\n");
            var atendimentosEmAndamento = Atendimentos.Where(a => a.StatusAtendimento == StatusAtendimentoEnum.EmAtendimento).ToList();
            for (int i = 0; i < atendimentosEmAndamento.Count(); i++) // procura na lista todos os atendimentos que estão em andamento
            {
                var atendimento = atendimentosEmAndamento[i];
                var contador = 1;
                Console.WriteLine($"Atendimento: {atendimento.Id} \n" +
                                  $"Cliente: {atendimento.Cliente.Nome} - CPF: {atendimento.Cliente.CPF} - Idade: {atendimento.Cliente.Idade} \n" +
                                  $"Assuntos: {string.Join("", atendimento.Assuntos.Select(a => $"\n{contador++}. {a.Descricao} - Tipo: {a.TipoAssunto.Descricao}"))}");
                Console.WriteLine("------------------------------------\n");
            }

            Console.WriteLine($"\nDigite o CPF do cliente para encerrar o atendimento");
            var cpf = Console.ReadLine();
            var atendimentoCliente = Atendimentos.FirstOrDefault(a => a.Cliente.CPF == cpf);
            if (atendimentoCliente == null)
            {
                Console.WriteLine($"Atendimento não encontrado para o CPF digitado");
                Console.ReadKey();
                MenuPrincipal();
                return;
            }

            for (int i = 0; i < atendimentoCliente.Assuntos.Count(); i++)
            {
                var assunto = atendimentoCliente.Assuntos[i];
                Console.WriteLine($"Informe a providência tomada para o assunto: {i + 1} - {assunto.Descricao}");
                assunto.Providencia = Console.ReadLine();
                assunto.DuracaoAtendimento = DateTime.Now - atendimentoCliente.HoraAtendimento.Value;
            }
            atendimentoCliente.StatusAtendimento = StatusAtendimentoEnum.Encerrado;
            Console.WriteLine($"Atendimento encerrado com sucesso");
            Console.ReadKey();
        }

        private static void ExibirAtendimento(Atendimento atendimento)
        {
            var contador = 1;
            Console.WriteLine($"Atendimento: {atendimento.Id} \n" +
                              $"Cliente: {atendimento.Cliente.Nome} - CPF: {atendimento.Cliente.CPF} - Idade: {atendimento.Cliente.Idade} \n" +
                              $"Assuntos: {string.Join("", atendimento.Assuntos.Select(a => $"\n{contador++}. {a.Descricao} - Tipo: {a.TipoAssunto.Descricao}"))}");

            Console.WriteLine("Pressione qualquer tecla para continuar");
            Console.ReadKey();
        }

        private static void Recepcionar()
        {
            Console.WriteLine($"Informe o nome do cliente: ");
            var nomeCliente = Console.ReadLine();

            Console.WriteLine($"Informe o CPF do cliente: ");
            var cpfCliente = Console.ReadLine();

            Console.WriteLine($"Informe a idade do cliente: ");
            var idadeCliente = Convert.ToInt32(Console.ReadLine());

            var cliente = new Cliente(cpfCliente, nomeCliente, idadeCliente);
            var assuntos = RegistrarAssuntos(null);
            var atendimento = new Atendimento(cliente, assuntos, StatusAtendimentoEnum.AguardandoAtendimento, DateTime.Now);
            Atendimentos.Add(atendimento);
        }

        private static List<Assunto> RegistrarAssuntos(List<Assunto> assuntos)
        {
            if (assuntos == null)
                assuntos = new List<Assunto>();

            Console.WriteLine($"---- Informe o Tipo do Assunto ---- \n Digite apenas números");
            for (int i = 0; i < TiposAssunto.Count(); i++)
            {
                var tipo = TiposAssunto[i];
                Console.WriteLine($"{tipo.Id} - {tipo.Descricao} ");
            }

            var tipoAssuntoInput = Console.ReadLine();
            var tipoAssuntoId = Convert.ToInt32(tipoAssuntoInput);

            Console.WriteLine($"Informe a Descrição do assunto :");
            var descricaoAssunto = Console.ReadLine();

            assuntos.Add(new Assunto(tipoAssuntoId, descricaoAssunto));

            Console.WriteLine($"Deseja adicionar outro assunto? 1. Sim - 2. Não");
            var opcaoAdicionarAssunto = Convert.ToInt32(Console.ReadLine());

            if (opcaoAdicionarAssunto == 1)
                RegistrarAssuntos(assuntos);

            return assuntos;
        }

        public class Atendimento
        {
            public Atendimento(Cliente cliente, List<Assunto> assuntos, StatusAtendimentoEnum statusAtendimento, DateTime horaChegada)
            {
                Id = Guid.NewGuid();
                Cliente = cliente;
                Assuntos = assuntos;
                StatusAtendimento = statusAtendimento;
                HoraChegada = horaChegada;
            }

            public Atendimento(Cliente cliente, List<Assunto> assuntos, StatusAtendimentoEnum statusAtendimento, DateTime horaChegada, DateTime horaAtendimento)
            {
                Id = Guid.NewGuid();
                Cliente = cliente;
                Assuntos = assuntos;
                StatusAtendimento = statusAtendimento;
                HoraChegada = horaChegada;
                HoraAtendimento = horaAtendimento;
            }

            public Guid Id { get; set; }
            public Cliente Cliente { get; set; }
            public List<Assunto> Assuntos { get; set; }
            public StatusAtendimentoEnum StatusAtendimento { get; set; }
            public DateTime HoraChegada { get; set; }
            public DateTime? HoraAtendimento { get; set; }
        }

        public class Cliente
        {
            public Cliente(string cpf, string nome, int idade)
            {
                Id = Guid.NewGuid();
                CPF = cpf;
                Nome = nome;
                Idade = idade;
            }

            public Guid Id { get; set; }
            public string CPF { get; set; }
            public string Nome { get; set; }
            public int Idade { get; set; }
        }

        public class Assunto // 5 formas possíveis de chamar a classe Assunto, foram criadas varios asssuntos, pois para já dar o exemplo de como será descrito
        {
            public Assunto(int tipoAssuntoId, string descricao) 
            {
                Id = Guid.NewGuid();
                TipoAssuntoId = tipoAssuntoId;
                TipoAssunto = TiposAssunto.FirstOrDefault(t => t.Id == tipoAssuntoId);
                Descricao = descricao;
            }

            public Assunto(TipoAssunto tipoAssunto, string descricao)
            {
                Id = Guid.NewGuid();
                TipoAssunto = tipoAssunto;
                TipoAssuntoId = tipoAssunto.Id;
                Descricao = descricao;
            }

            public Assunto(int tipoAssuntoId, string descricao, TimeSpan duracaoAtendimento)
            {
                Id = Guid.NewGuid();
                TipoAssuntoId = tipoAssuntoId;
                TipoAssunto = TipoAssunto = TiposAssunto.FirstOrDefault(t => t.Id == tipoAssuntoId);
                Descricao = descricao;
                DuracaoAtendimento = duracaoAtendimento;
            }

            public Assunto(int tipoAssuntoId, string descricao, string providencia, TimeSpan duracaoAtendimento)
            {
                Id = Guid.NewGuid();
                TipoAssuntoId = tipoAssuntoId;
                TipoAssunto = TipoAssunto = TiposAssunto.FirstOrDefault(t => t.Id == tipoAssuntoId);
                Descricao = descricao;
                DuracaoAtendimento = duracaoAtendimento;
                Providencia = providencia;
            }

            public Assunto(int tipoAssuntoId, string descricao, string providencia)
            {
                Id = Guid.NewGuid();
                TipoAssuntoId = tipoAssuntoId;
                TipoAssunto = TipoAssunto = TiposAssunto.FirstOrDefault(t => t.Id == tipoAssuntoId);
                Providencia = providencia;
            }

            public Guid Id { get; set; } 
            public int TipoAssuntoId { get; set; }
            public TipoAssunto TipoAssunto { get; set; } // reconhece o objeto do assunto pro Id
            public string Descricao { get; set; } // descrição do tipo do assunto
            public TimeSpan DuracaoAtendimento { get; set; } // serve para salvar quanto tempo demorou para atender o cliente
            public string Providencia { get; set; }
        }

        public enum StatusAtendimentoEnum
        {
            AguardandoAtendimento = 1,
            EmAtendimento = 2,
            Encerrado = 3,
        }

        public class TipoAssunto // esse é o construtor do Tipo de assunto. Aqui explica como é a chamada do TipoAssunto
        {
            public TipoAssunto(int id, string descricao, int grauUrgencia)
            {
                Id = id;
                Descricao = descricao;
                GrauUrgencia = grauUrgencia;
            }

            public int Id { get; set; }
            public string Descricao { get; set; }
            public int GrauUrgencia { get; set; }
        }

        private static void CarregarTipoAssuntos()
        {
            TiposAssunto.AddRange(new List<TipoAssunto>() { // estipulados apenas 5 assuntos neste trabalho, mas eventualmente poderiam ter mais com outros graus de urgência que não se repetissem
                new TipoAssunto(1, "Abertura Conta", 1), // tipo, nome e grau de urgencia
                new TipoAssunto(2, "Empréstimo", 2),
                new TipoAssunto(3, "Bloqueio de cartão", 3),
                new TipoAssunto(4, "Título de captalização", 4),
                new TipoAssunto(5, "Cancelamento de conta", 5) // maior prioridade, sem grandes diferenças entre elas, só 1 grau para exemplificar
            });
        }

        private static void CarregarDadosTeste() // aqui é onde está escrito o que foi inserido lá como 1a função do void main , em cima da lista, sendo atribuidos dados teste como exemplo
        {
            CarregarTipoAssuntos();
            Atendimentos.AddRange(new List<Atendimento>() { // aqui são colocadas algumas pessoas inventadas naquela lista de atendimento e dai vai criando.
                new Atendimento(new Cliente("774.422.820-08", "Jose", 20), // dentro de cada atendimento, tem o cliente com suas propriedades, CPF, nome e idade
                                new List<Assunto>(){ // lista de assunto que o cliente quer fazer. Lista de assunto dentro deste atendimento (objeto) dentro dele, tem cliente, assunto, tipo de atendimento resolvido ou nao e a hora do atendimento
                                new Assunto(1,"Quero abrir conta poupança"), // exemplo das infos descritas na linha 247
                                new Assunto(2,"Quero um empréstimo consignado") },
                                StatusAtendimentoEnum.AguardandoAtendimento,
                                DateTime.Now.AddMinutes(-100)),
                new Atendimento(new Cliente("147.553.850-24", "Joao", 65),
                                new List<Assunto>(){
                                new Assunto(2,"Quero empréstimo com juros baixo") },
                                StatusAtendimentoEnum.EmAtendimento,
                                DateTime.Now.AddMinutes(-30),
                                DateTime.Now.AddMinutes(-2)),
                new Atendimento(new Cliente("526.898.840-97", "Maria", 18),
                                new List<Assunto>(){
                                new Assunto(3,"Meu cartão foi roubado","Bloqueio realizado",TimeSpan.FromMinutes(20))}, // id, descrição, providencia tomada, tempo
                                StatusAtendimentoEnum.Encerrado,
                                horaChegada:DateTime.Now.AddMinutes(-20),
                                horaAtendimento:DateTime.Now.AddMinutes(-10)),
                new Atendimento(new Cliente("366.247.950-85", "Josefa", 50),
                                new List<Assunto>(){
                                new Assunto(3,"Meu cartão foi clonado", "Bloqueio realizado",TimeSpan.FromMinutes(25)),
                                new Assunto(5,"Quero cancelar a conta", "Conta cancelada", TimeSpan.FromMinutes(20)) },
                                StatusAtendimentoEnum.Encerrado,
                                DateTime.Now.AddMinutes(-5),
                                DateTime.Now),
                new Atendimento(new Cliente("442.996.130-15", "Marlene", 32),
                                new List<Assunto>(){
                                new Assunto(5,"Cancelamento de conta-salário", "Conta salário foi fechada", TimeSpan.FromMinutes(30)),
                                new Assunto(1,"Abri conta poupança", "Conta poupança aberta", TimeSpan.FromMinutes(5))},
                                StatusAtendimentoEnum.Encerrado,
                                DateTime.Now.AddMinutes(-35),
                                DateTime.Now.AddMinutes(-15)),
            });

        }
    }
}
