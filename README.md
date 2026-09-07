# Workspace Spring Boot 4 + JPA

API REST completa em Java, simulando o backend de um sistema de e-commerce, desenvolvida com **Spring Boot 4** e **Spring Data JPA**. Projeto construído com arquitetura em camadas (entidades, repositórios, serviços e controllers), incluindo tratamento de exceções personalizado e suporte a múltiplos bancos de dados.

## 📋 Sobre o projeto

O sistema modela o domínio de uma loja virtual — usuários, produtos, categorias, pedidos, itens de pedido e pagamentos — expondo operações de CRUD via endpoints REST para cada recurso. É uma evolução direta dos projetos anteriores de JDBC/DAO do autor, agora usando **Spring Data JPA** para eliminar SQL manual e **Hibernate** para o mapeamento objeto-relacional.

## 🗂️ Modelo de domínio

- **`User`** — usuários/clientes do sistema, relacionados a múltiplos pedidos.
- **`Product`** — produtos, relacionados a categorias (N:N) e a itens de pedido.
- **`Category`** — categorias de produtos.
- **`Order`** — pedidos, relacionados a um usuário, com status (`OrderStatus`) e pagamento.
- **`OrderItem`** — itens de um pedido (associação N:N entre `Order` e `Product`), com chave composta própria (`OrderItemPK`), incluindo quantidade e preço no momento da compra.
- **`Payment`** — pagamento de um pedido (relação 1:1).

## ✨ Funcionalidades

CRUD completo via API REST para `User`, `Product`, `Category` e consulta de `Order`:
- `GET /recurso` — lista todos os registros.
- `GET /recurso/{id}` — busca por ID.
- `POST /recurso` — cria um novo registro (retorna `201 Created` com a URI do recurso criado).
- `PUT /recurso/{id}` — atualiza um registro existente.
- `DELETE /recurso/{id}` — remove um registro.

**Tratamento de erros:** um `ResourceExceptionHandler` global (`@ControllerAdvice`) captura exceções como `ResourceNotFoundException` (404) e `DatabaseException` (erros de integridade referencial), retornando um corpo de erro padronizado (`StandardError`) para qualquer controller da aplicação. Por ora, o lançamento dessas exceções nos métodos de serviço (`try/catch` em `delete`/`update`) foi implementado apenas em `UserService`, como exercício de aprendizado — as demais entidades ainda usam apenas o CRUD básico do Spring Data JPA, sem esse tratamento específico.

**Dados de teste automáticos:** o perfil `test` (`TestConfig`) popula o banco H2 automaticamente ao iniciar a aplicação, facilitando testes manuais via Postman sem precisar cadastrar dados manualmente.

## 🛠️ Tecnologias utilizadas

- **Java 25**
- **Spring Boot 4.1**
- **Spring Data JPA** / **Hibernate**
- **H2 Database** — banco em memória para desenvolvimento/testes, com console web habilitado
- **PostgreSQL** — banco relacional para produção
- **Maven** — gerenciamento de dependências e build
- **Postman** — testes manuais dos endpoints da API

## 📁 Estrutura do projeto

```
workspace-springboot4-jpa/
└── src/main/java/com/educandoweb/course/
    ├── entities/
    │   ├── User.java, Product.java, Category.java
    │   ├── Order.java, OrderItem.java, Payment.java
    │   ├── PK/OrderItemPK.java          # Chave composta de OrderItem
    │   └── enums/OrderStatus.java
    ├── repositories/                     # Interfaces Spring Data JPA
    │   ├── UserRepository.java
    │   ├── ProductRepository.java
    │   ├── CategoryRepository.java
    │   ├── OrderRepository.java
    │   └── OrderItemRepository.java
    ├── services/                          # Regras de negócio
    │   ├── UserService.java, ProductService.java
    │   ├── CategoryService.java, OrderService.java
    │   └── exceptions/ (DatabaseException, ResourceNotFoundException)
    ├── resources/                          # Controllers REST
    │   ├── UserResource.java, ProductResource.java
    │   ├── CategoryResource.java, OrderResource.java
    │   └── exceptions/ (ResourceExceptionHandler, StandardError)
    ├── config/TestConfig.java              # Popula dados de teste (perfil "test")
    └── CourseApplication.java              # Classe principal
```

## ▶️ Como executar

### Pré-requisitos
- JDK 25
- Maven (ou usar o wrapper `mvnw` incluso no projeto)

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/rui-fernando/workspace-springboot4-jpa.git
   cd workspace-springboot4-jpa
   ```
2. Execute a aplicação (por padrão, sobe com o banco H2 em memória):
   ```bash
   ./mvnw spring-boot:run
   ```
3. A API estará disponível em `http://localhost:8080`. Endpoints de exemplo:
   - `GET http://localhost:8080/users`
   - `GET http://localhost:8080/products`
   - `GET http://localhost:8080/orders`
4. O console do H2 fica disponível em `http://localhost:8080/h2-console` (verifique a URL JDBC configurada em `application.properties`).
5. Para testar os endpoints manualmente, importe as rotas no **Postman** ou use `curl`/`HTTPie`.

## 🔜 Próximos passos

- Estender o tratamento de exceções personalizado (`ResourceNotFoundException`/`DatabaseException`) para `ProductService`, `CategoryService` e `OrderService`, atualmente implementado apenas em `UserService`.
- Extrair uma classe de serviço genérica (`GenericService<T, ID>`) para reaproveitar a lógica de CRUD e tratamento de exceção entre os services, reduzindo duplicação de código.

## 👤 Autor

Desenvolvido por [Rui Fernando](https://github.com/rui-fernando), estudante de Ciência da Computação na Universidade Estadual da Paraíba (UEPB).
