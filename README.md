<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  
</head>
<body>
  <h1>🚀 Desafio Técnico - Della Volpe</h1>

  <h2>📌 Objetivo</h2>
  <p>
    Aplicação fullstack (Java Spring Boot + Angular) para cadastro de usuários e seus endereços,
    com validação de CEP via API ViaCEP.
  </p>

  <h2>🧰 Tecnologias</h2>
  <ul>
    <li>Java 17 + Spring Boot</li>
    <li>Angular 19+ standalone</li>
    <li>Banco de Dados H2 (persistência em memória)</li>
    <li>API ViaCEP</li>
  </ul>

  <h2>▶️ Como executar o projeto</h2>

  <h3>Backend</h3>
  <ol>
    <li>Clone o repositório: <code>git clone https://github.com/irv3nge/desafio-della-volpe</code></li>
    <li>Abra o projeto em sua IDE (IntelliJ ou VSCode)</li>
    <li>Execute a aplicação Spring Boot</li>
    <li>Acesse a API: <code>http://localhost:8080/api/usuarios</code></li>
    <li>Swagger disponível (opcional): <code>http://localhost:8080/swagger-ui/index.html</code></li>
  </ol>

  <h3>Frontend</h3>
  <ol>
    <li>Entre na pasta do frontend</li>
    <li>Instale as dependências: <code>npm install</code></li>
    <li>Execute: <code>ng serve</code></li>
    <li>Acesse: <code>http://localhost:4200</code></li>
  </ol>

  <h2>🔗 Endpoints Backend</h2>

  <h3>👤 Usuários</h3>
  <ul>
    <li><strong>GET /api/usuarios</strong> — Lista todos os usuários</li>
    <li><strong>GET /api/usuarios/{id}</strong> — Retorna usuário com endereços</li>
    <li><strong>POST /api/usuarios</strong> — Cria um usuário com endereços
      <pre><code>{
  "nome": "João Silva",
  "email": "joao@email.com",
  "telefone": "11999999999",
  "enderecos": [{
    "cep": "01001-000",
    "rua": "Rua Teste",
    "numero": "123",
    "estado": "SP",
    "cidade": "São Paulo",
    "bairro": "Centro"
  }]
}</code></pre>
    </li>
    <li><strong>PUT /api/usuarios/{id}</strong> — Atualiza usuário com endereços</li>
    <li><strong>DELETE /api/usuarios/{id}</strong> — Remove usuário e endereços</li>
  </ul>

  <h3>🏠 Endereços</h3>
  <ul>
    <li><strong>POST /api/usuarios/{usuarioId}/enderecos</strong> — Cria um novo endereço</li>
    <li><strong>PUT /api/usuarios/{usuarioId}/enderecos/{id}</strong> — Atualiza endereço</li>
    <li><strong>DELETE /api/usuarios/{usuarioId}/enderecos/{id}</strong> — Remove endereço</li>
  </ul>

  <h2>🖥️ Funcionalidades do Frontend (Angular)</h2>
  <ul>
    <li>Formulário dinâmico com múltiplos endereços</li>
    <li>Validação de campos obrigatórios (nome, email, telefone, etc)</li>
    <li>Validação automática de CEP via API do ViaCEP</li>
    <li>Mensagem visual de sucesso/erro em:
      <ul>
        <li>Criação do usuário</li>
        <li>Erro no CEP (exibe “CEP inválido” abaixo do campo)</li>
        <li>Erro na API (backend desligado)</li>
      </ul>
    </li>
    <li>Spinner visual durante carregamento</li>
  </ul>

  <h2>✅ Cobertura dos Requisitos Técnicos</h2>
  <ul>
    <li>✔️ API RESTful com todos os endpoints esperados</li>
    <li>✔️ Validação de CEP via ViaCEP (com bloqueio se inválido)</li>
    <li>✔️ CRUD completo de usuários e endereços</li>
    <li>✔️ Interface Angular integrada com mensagens visuais</li>
  </ul>

  <h2>📁 Link do Projeto</h2>
  <p><a href="https://github.com/irv3nge/desafio-della-volpe" target="_blank">https://github.com/irv3nge/desafio-della-volpe</a></p>
</body>
</html>
