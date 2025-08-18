# Sinal IF ⏰

## Contexto 📖
- **Instituição**: Instituo Federal de Educação, Ciência e Tecnologia do Triângulo Mineiro - Campus Patrocínio.
- **Curso**: curso superior de Tecnologia em Análise e Desenvolvimento de Sistemas (ADS).
- **Disciplinas**: Sistemas Distribuídos, Desenvolvimento de Aplicações Web II
- **Docentes**: Cícero Lima Costa, Lucas Gonçalves Cunha
- **Discentes**: [Bruno Eugênio Santos](https://github.com/brunoeugeniodev) e [João Augusto Marciano Silva](https://github.com/joaomarcianodev)

## Descrição do projeto ✍
 > A ideia do Sinal IF é fruto de uma necessidade encontrada pelos técnicos administrativos do IFTM Campus Patrocínio que, antes deste projeto, utilizam um aplicativo muito antigo (no qual nem se é mais possível realizar manutenções técnicas) que aciona uma música em horários programados para indicar a troca de horários entre as aulas (7:10, 8:00, 8:50), e também para indicar o início do intervalo (9:40 até 10:00). Devida as condições em que se encontra o aplicativo e a forma como este é manuseado pelos servidores, pareceu-se conveniente e benéfico à instituição recriar o aplicativo com tecnologias atuais e que ofereça mais funcionalidades aos usuários. Logo este projeto contribui não somente para o portifólio e aprendizado dos discentes como também beneficia a instituição no qual se fazem parte.

## Desenvolvedores 👨‍💻
- Bruno Eugênio Santos
- João Augusto Marciano Silva (autor principal)

## Tecnologias utilizadas 📈
| Spring | Bootstrap | jQuery | PostgreSQL | Youtube IFRAME API |
| :---: | :---: | :---: | :---: | :---: |
| <img src="https://miro.medium.com/v2/resize:fit:720/format:webp/1*8QxPAk1bQFmTrkuLERm1wQ.png" height="40" alt="Spring logo"/> | <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Bootstrap_logo.svg/1280px-Bootstrap_logo.svg.png" height="40" alt="Bootstrap logo"/> | <img src="https://cdn.iconscout.com/icon/free/png-256/free-jquery-8-1175153.png?f=webp" height="40" alt="jQuery logo"/> | <img src="https://upload.wikimedia.org/wikipedia/commons/2/29/Postgresql_elephant.svg" height="40" alt="PostgresSQL logo"/> | <img src="https://upload.wikimedia.org/wikipedia/commons/e/ef/Youtube_logo.png" height="40" alt="Youtube API logo"/> |

## Divisão do Projeto 💻

> O repositório SD-sinalif e Web2-sinalif são independentes e não comunicam entre si. A divisão ocorreu pois na verdade o projeto final (Web2-sinalif) foi reaproveitado do projeto de sistemas distribuídos.

### SD-sinalif
- ✅ Possui 3 serviços para manipular o Banco de Dados:
  - Serviço 1: Java Spring com PostgreSQL
  - Serviço 2: Flask com MongoDB
  - Serviço 3: Java Spring com PostgreSQL
- ✅ Utiliza um gestor que gerencia o CRUD entre as tabelas
- ✅ Cliente comunica somente com o gestor para utilizar o CRUD
- ✅ Formato de API
- ❌ Login de usuários
- ❌ Configuração de segurança das rotas

### Web2-sinalif
- ✅ A própria aplicação gerencia as requisições
- ✅ Cliente acessa via web
- ✅ Login de usuários
- ✅ Configuração de segurança das rotas
- ❌ Página de configurações na visão do cliente
  - ❌ Alterar nome, email, senha e foto de perfil
  - ❌ Método para confirmar email
  - ❌ Controle de notificações
- ❌ Método de recuperação de senha
- ❌ Aviso de segurança para funções críticas
- ❌ Reprodutor automático das músicas nos horários programados

## Próximas atualizações 🔜
- ✔ Página de configurações na visão do cliente
- ✔ Reprodutor automático das músicas nos horários programados

## Demais links do projeto 🔗
- Distribuição dos Serviços - [Canva](https://www.canva.com/design/DAGpgXhzyDs/rK6owBbFCKk0CrfOZMakXw/edit?utm_content=DAGpgXhzyDs&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)
- Diagrama do Banco de Dados - [dbdiagram.io](https://dbdiagram.io/d/Sinal-IF-6838428cbd74709cb71255d4) ⚠ DESATUALIZADO ⚠
- Plano de Atividades - [Trello](https://trello.com/b/XsppoiAi/sinal-if) ⚠ DESATUALIZADO ⚠
