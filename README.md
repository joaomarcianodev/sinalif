# Sinal IF
O seu sistema para controle de alarmes e músicas com sinais mais divertidos ao invés de uma simples sirene.

## Situação atual do projeto 📆

> **👨‍💻 Desenvolvedores atuantes**: 1 (João Augusto Marciano Silva) <br>
> **🕐 Tempo decorrido**: 3 meses (desde 26/05/2025) <br>
> **⏰ Tempo restante**: 3 meses (até 19/12/12025)

### Próximas atualizações 🔜
- ✔ Tela de configurações
- ✔ Função de confirmar email
- ✔ Função "esqueci minha senha"
- ✔ Função "alterar senha"
- ✔ Correção do banco de dados segundo as normas formais
- ✔ Melhoria de estilização e identidade visual do sistema
- ✔ Implementação do reprodutor automático via Youtube
- ✔ Implementação de backup automático

## Descrição do projeto ✍

- IDEIA E NECESSIDADE: <br>
A ideia do Sinal IF é fruto de uma necessidade encontrada pelos técnicos administrativos do IFTM Campus Patrocínio que, antes deste projeto (AS-IS), utilizam um aplicativo muito antigo (no qual nem se é mais possível realizar manutenções técnicas) que aciona uma música em horários programados para indicar a troca de horários entre as aulas (ex.: 7:10, 8:00, 8:50), e também para indicar o início do intervalo (ex.: 9:40 até 10:00). Devida as condições em que se encontra o aplicativo e a forma como este é manuseado pelos servidores, pareceu-se conveniente e benéfico à instituição recriar o aplicativo com tecnologias atuais e que ofereça mais funcionalidades à todos usuários diretos e indiretos.

- PROPOSTA DO PROJETO: <br>
O projeto em si (TO-BE) constitui-se em criar um sistema web que possua o controle de horários programados (alarmes/sinais) e que toque uma música aleatória e respeite as restrições de uso dos alarmes (tempo, randomização e etc). Com a função principal do projeto (core) finalizado, também há como objetivo do projeto implementar algumas funcionalidades que não existiam no antigo sistema, sendo estes: a possibilidade de cadastrar uma pausa programada para que dentro de um determinado período de tempo não seja tocado nenhum alarme; Uso de etiquetas para controlar um grupo de alarmes; Disponiiblizar diferentes visualizações dos alarmes (ordenar por dia, ordenar por execução e etc); Possibilitar que os alunos da instituição sugiram novas músicas para o sistema, logo também possibilitar que um funcionário (sub administrador) controle as músicas do sistema para aceitar ou negar uma sugestão, isto além de também ter controle das demais funcionalidades gerais do sistema (CRUD de alarmes, pausas, músicas, etiquetas e susgestões).

- EVOLUÇÃO DO PROJETO:
  - ✅ Planejamento
  - ✅ Coleta de requisitos
  - ✅ Modelagem do Banco de Dados
  - ✅ Divisão do banco de dados em 3 serviços (2 PostgreSQL e 1 MongoDB)
  - ✅ Codificação do CRUD de todas tabelas (em serviços diferentes)
  - ✅ Codificação do Gestor para consumir os serviços como API
  - ✅ Codificação do cliente web para controlar o sistema como API
  - ✅ Implementação da API do Youtube para testar as músicas <br>
**[Finalizado trabalho para a disciplina Sistema Distribuídos]**
  - ✅ Unificação do sistema em um único projeto e banco (PostgreSQL)
  - ✅ Transformação de RestController para Controller
  - ✅ Adaptação do projeto devido remoção da API
  - ✅ Codificação do sistema de login via Spring Security
  - ✅ Codificação de perfis com Thymeleaf
  - ✅ Atribuição de permissões à rotas e páginas conforme o perfil
  - ✅ Codificação dos testes de integração e testes unitários
  - ✅ Padronização do estilo do sistema com Bootstrap <br>
**[Finalizado trabalho para a disciplina Desenvolvimento de Aplicações Web II e Extensão II]** <br>
**[Finalizada contribuição do desenvolvedor Bruno Eugênio Santos]**
  - ❌ Padronização de variáveis, funções e etc.
  - ❌ Otimização de funções complexas
  - ❌ Codificação da execução da pausa programada
  - ❌ Codificação do instalador do sistema
  - ❌ Codificação do reprodutor automático
  - ❌ Codificação da tela de testes
  - ❌ Codificação da tela de configurações
  - ❌ Codificação da função de confirmar email
  - ❌ Codificação da função "esqueci minha senha"
  - ❌ Codificação da função "alterar senha"
  - ❌ Correção do banco de dados segundo as normas formais
  - ❌ Melhoria de estilização e identidade visual do sistema
  - ❌ Implementação do reprodutor automático via Youtube
  - ❌ Implementação de backup automático

## Sobre o Sistema

### Documentos de orientação [em desenvolvimento]
> [Inicialização automática](https://github.com/CTIC-PTC-IFTM/CTIC-sinalif/blob/main/resources/Inicialização%20Automática/Orientações%20da%20Inicialização.md) <br>
> [API Sinal IF](https://github.com/CTIC-PTC-IFTM/CTIC-sinalif/blob/main/resources/API_sinalif/Orientações%20da%20API.md) <br>
> Banco de Dados

### Tecnologias utilizadas 📈
| Spring | Bootstrap | jQuery | PostgreSQL | Youtube IFRAME API |
|:---:|:---:|:---:|:---:|:---:|
| <img src="https://miro.medium.com/v2/resize:fit:720/format:webp/1*8QxPAk1bQFmTrkuLERm1wQ.png" height="40" alt="Spring logo"/> | <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Bootstrap_logo.svg/1280px-Bootstrap_logo.svg.png" height="40" alt="Bootstrap logo"/> | <img src="https://cdn.iconscout.com/icon/free/png-256/free-jquery-8-1175153.png?f=webp" height="40" alt="jQuery logo"/> | <img src="https://upload.wikimedia.org/wikipedia/commons/2/29/Postgresql_elephant.svg" height="40" alt="PostgresSQL logo"/> | <img src="https://upload.wikimedia.org/wikipedia/commons/e/ef/Youtube_logo.png" height="40" alt="Youtube API logo"/> |

### Anexos do projeto 🔗
- Plano de Atividades - [Trello](https://trello.com/b/XsppoiAi/sinal-if) ⚠ DESATUALIZADO ⚠

### Informações do acadêmicas 📖
- **🎒 Instituição**: Instituo Federal de Educação, Ciência e Tecnologia do Triângulo Mineiro - Campus Patrocínio.
- **🖱️ Curso**: curso superior de Tecnologia em Análise e Desenvolvimento de Sistemas (ADS).
- **📚 Disciplinas envolvidas**: Sistemas Distribuídos, Desenvolvimento de Aplicações Web II, Extensão II e Extensão III.
- **📖 Docentes orientadores**: Cícero Lima Costa, Lucas Gonçalves Cunha, Cintia Carvalho Oliveira e Jean Lucas de Sousa.
- **👨‍💻 Discentes contribuidores**:
  - [João Augusto Marciano Silva](https://github.com/joaomarcianodev) - Responsável por toda direção e execução do projeto, desde a idea e planejamento até a entrega final com o sistema em produção no IFTM.
  - [Bruno Eugênio Santos](https://github.com/brunoeugeniodev) - Reponsável por desenvolver boa parte do CRUD das tabelas iniciais (core) do projeto e por desenvolvedor os testes automatizados. 
