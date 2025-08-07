<!DOCTYPE html>

  <title>README API - Desafio Técnico</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      line-height: 1.6;
      margin: 20px auto;
      max-width: 960px;
      color: #2c3e50;
    }
    h1, h2, h3 {
      color: #34495e;
    }
    code {
      background: #f4f4f4;
      padding: 2px 6px;
      border-radius: 4px;
      font-family: monospace;
    }
    pre {
      background: #f9f9f9;
      padding: 12px;
      border-radius: 6px;
      overflow-x: auto;
      border-left: 6px solid #ccc;
    }
    .status {
      display: inline-block;
      padding: 4px 8px;
      border-radius: 4px;
      font-weight: bold;
      font-size: 13px;
    }
    .ok {
      background: #2ecc71;
      color: #fff;
    }
    .fail {
      background: #e74c3c;
      color: #fff;
    }
    ul {
      padding-left: 20px;
    }
    hr {
      margin: 30px 0;
    }
  </style>
</head>
<body>

  <h1>📘 API REST - Desafio Técnico</h1>
  <p>Este projeto é uma API RESTful desenvolvida com Spring Boot para gerenciar usuários e seus endereços, com validação de CEP usando a API pública ViaCEP.</p>

  <h2>✅ Funcionalidades e Status</h2>
  <ul>
    <li><strong>Listar todos os usuários</strong>: <span class="status ok">OK</span></li>
    <li><strong>Buscar usuário por ID</strong>: <span class="status ok">OK</span></li>
    <li><strong>Criar usuário + endereço</strong>: <span class="status ok">OK</span></li>
    <li><strong>Atualizar usuário + endereço</strong>: <span class="status ok">OK</span></li>
    <li><strong>Remover usuário + endereços</strong>: <span class="status ok">OK</span></li>
    <li><strong>Validação de CEP com ViaCEP</strong>: <span class="status ok">OK</span></li>
    <li><strong>Impedir criação/edição com CEP inválido</strong>: <span class="status ok">OK</span></li>
  </ul>

  <hr>

  <h2>🧪 Endpoints</h2>

  <h3>1. Listar Todos os Usuários</h3>
  <p><strong>GET</strong> <code>/api/usuarios</code></p>
  <pre><code>curl -X GET http://localhost:8080/api/usuarios</code></pre>

  <h3>2. Buscar Usuário por ID</h3>
  <p><strong>GET</strong> <code>/api/usuarios/{id}</code></p>
  <pre><code>curl -X GET http://localhost:8080/api/usuarios/1</code></pre>

  <h3>3. Criar Usuário com Endereço</h3>
  <p><strong>POST</strong> <code>/api/usuarios</code></p>
  <pre><code>curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Pedro Jorge",
    "email": "pedro@email.com",
    "telefone": "11999999999",
    "enderecos": [
      {
        "cep": "01001000",
        "rua": "Praça da Sé",
        "numero": "100",
        "estado": "SP",
        "cidade": "São Paulo",
        "bairro": "Sé"
      }
    ]
  }'</code></pre>

  <h3>4. Atualizar Usuário + Endereços</h3>
  <p><strong>PUT</strong> <code>/api/usuarios/{id}</code></p>
  <pre><code>curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Pedro Atualizado",
    "email": "novo@email.com",
    "telefone": "11988888888",
    "enderecos": [
      {
        "cep": "02020000",
        "rua": "Rua Nova",
        "numero": "200",
        "estado": "SP",
        "cidade": "São Paulo",
        "bairro": "Centro"
      }
    ]
  }'</code></pre>

  <h3>5. Deletar Usuário</h3>
  <p><strong>DELETE</strong> <code>/api/usuarios/{id}</code></p>
  <pre><code>curl -X DELETE http://localhost:8080/api/usuarios/1</code></pre>

  <hr>

  <h2>🔍 Validação de CEP</h2>
  <ul>
    <li>Durante a criação e edição, o backend faz uma chamada para a <strong>API do ViaCEP</strong>.</li>
    <li>Se o CEP for inválido (ou <code>endereco.erro == true</code>), a requisição é rejeitada com <code>400 Bad Request</code>.</li>
    <li>No frontend, é exibido o alerta visual abaixo do formulário: <code>CEP inválido</code>.</li>
  </ul>

  <hr>

  <h2>📦 JSON de Exemplo para Testes</h2>

  <h3>JSON válido para criação</h3>
  <pre><code>{
  "nome": "Maria da Silva",
  "email": "maria@teste.com",
  "telefone": "11991234567",
  "enderecos": [
    {
      "cep": "01001000",
      "rua": "Praça da Sé",
      "numero": "123",
      "estado": "SP",
      "cidade": "São Paulo",
      "bairro": "Sé"
    }
  ]
}</code></pre>

  <h3>JSON inválido com CEP inexistente</h3>
  <pre><code>{
  "nome": "Teste",
  "email": "teste@invalido.com",
  "telefone": "11991111111",
  "enderecos": [
    {
      "cep": "99999999",  // inválido
      "rua": "Fake",
      "numero": "999",
      "estado": "SP",
      "cidade": "SP",
      "bairro": "Fake"
    }
  ]
}</code></pre>

  <p>Retorno esperado:</p>
  <pre><code>{
  "message": "CEP inválido",
  "status": 400
}</code></pre>

  <hr>

  <h2>🔗 Repositório</h2>
  <p>Você pode acessar o projeto completo no GitHub:</p>
  <p><a href="https://github.com/irv3nge/desafio-della-volpe" target="_blank">https://github.com/irv3nge/desafio-della-volpe</a></p>

  <hr>

  <h2>📄 Licença</h2>
  <p>Este projeto é destinado exclusivamente ao desafio técnico proposto pela Della Volpe.</p>

</body>
</html>
