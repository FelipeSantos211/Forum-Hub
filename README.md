

# FórumHub - API de Tópicos 💬  
Este projeto foi desenvolvido como parte do desafio Alura + Oracle ONE e tem como objetivo criar um sistema de gerenciamento de tópicos para um fórum. A API permite que os usuários criem, visualizem, atualizem, excluam tópicos e respondam a eles. Além disso, os usuários podem excluir suas próprias respostas. A autenticação via JWT (JSON Web Token) é implementada para garantir que apenas o usuário criador do tópico ou da resposta possa atualizá-los ou excluí-los.

<p align="center">
  <img src="https://drive.google.com/uc?id=1Lg2qL4zv0NdKYmNzp6JRfi6JvPfcb2iu" width="200"/>
</p>

---

## 📋 Funcionalidades
- **Criar um novo tópico**: Permite ao usuário criar um tópico no fórum.
- **Mostrar todos os tópicos criados**: Exibe todos os tópicos disponíveis no fórum.
- **Mostrar um tópico específico**: Exibe detalhes de um tópico individual.
- **Atualizar um tópico**: Permite ao usuário editar informações de um tópico que ele tenha criado.
- **Excluir um tópico**: Permite ao usuário excluir um tópico que ele tenha criado.
- **Responder a um tópico**: Os usuários podem responder a tópicos com comentários.
- **Mostrar respostas de um tópico**: Exibe todas as respostas de um tópico específico.
- **Excluir uma resposta**: Permite ao usuário excluir uma resposta que ele tenha criado no tópico.
- **Autenticação com JWT**: A API utiliza autenticação via **JSON Web Token** para garantir que apenas o criador do tópico ou da resposta possa editar ou excluir.

---

## 🚀 Como Executar  
### Passos para rodar o projeto:

1. **Clone o repositório**:
   ```bash
   git clone <https://github.com/FelipeSantos211/Forum-Hub>
   cd Forum-Hub
   ```

2. **Compile e execute o projeto**:
   ```bash
   mvn spring-boot:run
   ```

3. **Interação via API**:
   Após iniciar o sistema, você pode fazer requisições HTTP usando ferramentas como Postman ou Curl. Alguns exemplos de endpoints incluem:
   - **POST /auth/register**: Registrar um novo usuário.
   - **POST /auth/login**: Fazer login e obter um token JWT.
   - **GET /topics**: Listar todos os tópicos.
   - **POST /topics**: Criar um novo tópico (somente usuários autenticados).
   - **PUT /topics/{id}**: Atualizar um tópico (somente o criador).
   - **DELETE /topics/{id}**: Excluir um tópico (somente o criador).
   - **POST /topics/{id}/responses**: Responder a um tópico.
   - **DELETE /topics/{id}/responses/{responseId}**: Excluir uma resposta (somente o criador).
   - **GET /topics/{id}/responses**: Listar respostas de um tópico.

---

## 🧑‍💻 Dependências  
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security**
- **JWT (JSON Web Token)**
- **PostgreSQL**

---

## 📄 Exemplo de Uso  
### Exemplo de interação com a API:

1. **Registrar um novo usuário**:
   ```bash
   POST /auth/register
   Content-Type: application/json
   {
     "email": "email@gmail.com",
     "password": "password123"
   }
   ```

2. **Fazer login e obter um token JWT**:
   ```bash
   POST /auth/login
   Content-Type: application/json
   {
     "username": "email@gmail.com",
     "password": "password123"
   }
   ```

   A resposta será algo como:
   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyMSJ9..."
   }
   ```

3. **Criar um novo tópico** (utilizando o token no cabeçalho):
   ```bash
   POST /topics
   Authorization: Bearer <token>
   Content-Type: application/json
   {
     "title": "Meu primeiro tópico",
     "content": "Conteúdo do tópico"
   }
   ```

4. **Atualizar um tópico** (somente o criador pode editar):
   ```bash
   PUT /topics/{id}
   Authorization: Bearer <token>
   Content-Type: application/json
   {
     "title": "Tópico atualizado",
     "content": "Conteúdo atualizado do tópico"
   }
   ```

5. **Excluir um tópico** (somente o criador pode excluir):
   ```bash
   DELETE /topics/{id}
   Authorization: Bearer <token>
   ```

6. **Responder a um tópico**:
   ```bash
   POST /topics/{id}/responses
   Authorization: Bearer <token>
   Content-Type: application/json
   {
     "content": "Essa é minha resposta ao tópico."
   }
   ```

7. **Excluir uma resposta** (somente o criador pode excluir):
   ```bash
   DELETE /topics/{id}/responses/{responseId}
   Authorization: Bearer <token>
   ```

---

## 🧪 Testes  
- O projeto contém funcionalidades que podem ser testadas diretamente através de requisições HTTP.
- Teste a criação, visualização, atualização e exclusão de tópicos e respostas.
- Verifique se a autenticação via JWT está funcionando corretamente, e se os tópicos e respostas são acessíveis apenas pelos usuários autenticados e criadores.

---

## 🛡️ Licença  
Este projeto é desenvolvido como parte do programa **Oracle Next Education** e está disponível para fins educacionais e de aprendizado.

---

## 👥 Autor  
- **Felipe Santos**  
Desenvolvido como parte do desafio **Alura + Oracle ONE**.

---

## 🌐 Links Úteis  
- [Documentação Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [JWT.io](https://jwt.io/)

---
