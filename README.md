# My Gallery API 

## 1. Descrição do Projeto

Este projeto é uma **API RESTful** desenvolvida em Spring Boot para gerenciar arquivos pessoais.

Seu objetivo principal é fornecer uma solução segura para:
* **Gerenciamento de Usuários (CRUD):** Permite criar, listar, atualizar e deletar contas de usuário.
* **Armazenamento de arquivos Seguro:** Integração com o **Amazon S3** para upload, listagem e exclusão de imagens, isolando o conteúdo por usuário.
* **Autenticação e Autorização:** Utiliza **JSON Web Tokens (JWT)** e **Spring Security** para proteger os endpoints, garantindo que apenas usuários autenticados possam acessar suas próprias imagens.

---

## 2. Componentes da Equipe (Autores) 

| Nome Completo | Função Principal |

| :--- | :--- | :--- |

| **[Gabriel Marley Cardoso dos Santos]** | Desenvolvedor Full stack |


---

## 3. Tecnologias Utilizadas 🛠️

O projeto é construído principalmente com a seguinte stack:

* **Linguagem:** Java
* **Framework:** Spring Boot, Spring Security
* **Armazenamento:** AWS S3 (Amazon Web Services Simple Storage Service)
* **Segurança:** JWT (JSON Web Tokens) e BCrypt
* **Persistência:** JPA / Hibernate (Implícito pelo uso de entidades e serviços)

---

## 4. Endpoints Principais (API)

A API expõe os seguintes grupos de recursos:

### 📸 Imagens (`/images`) - Requer JWT
| Método | Endpoint | Descrição | Controller |
| :--- | :--- | :--- | :--- |
| `GET` | `/images` | Lista todas as URLs de imagens do usuário autenticado (URLs pré-assinadas). | `S3Controller` |
| `POST` | `/images` | Faz o upload de uma nova imagem para a pasta S3 do usuário. | `S3Controller` |
| `DELETE` | `/images/{key}` | Exclui uma imagem do usuário pelo seu `key` (nome do arquivo). | `S3Controller` |

### 👤 Usuários (`/users`) - CRUD
| Método | Endpoint | Descrição | Controller |
| :--- | :--- | :--- | :--- |
| `POST` | `/users` | Cria um novo usuário. | `UserController` |
| `GET` | `/users/{id}` | Busca um usuário por ID. | `UserController` |
| `PUT` | `/users/{id}/replacement` | Atualiza os dados de um usuário (Ex: email). | `UserController` |
| `DELETE` | `/users/{id}` | Exclui um usuário. | `UserController` |

---

## 5. Configuração e Execução ⚙️

### Configuração de Variáveis de Ambiente

Este projeto **requer** a configuração das seguintes variáveis de ambiente:

| Variável | Descrição | Arquivo Relacionado |
| :--- | :--- | :--- |
| `JWT_SECRET` | Chave secreta para assinatura dos JWTs. | `JwtService.java` |
| `AWS_S3_BUCKET` | Nome do bucket S3 onde as imagens serão armazenadas. | `S3Service.java` |
| `AWS_ACCESS_KEY_ID` | Chave de acesso AWS para credenciais. | `S3Config.java` |
| `AWS_SECRET_ACCESS_KEY` | Chave secreta AWS para credenciais. | `S3Config.java` |

### Como Rodar

1.  **Clone o repositório:**
    ```bash
    git clone [https://aws.amazon.com/pt/what-is/repo/](https://aws.amazon.com/pt/what-is/repo/)
    cd my-gallery
    ```
2.  **Instale as dependências (Maven):**
    ```bash
    mvn clean install
    ```
3.  **Execute o projeto:**
    ```bash
    mvn spring-boot:run
    ```
    *A aplicação será iniciada na porta configurada (padrão 8080).*
