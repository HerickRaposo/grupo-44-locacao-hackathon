# GRUPO 44 Locação  Hackathon


# Arquivo jar da aplicação diretorório pacote , dentro de domínio.<BR> Nessa pasta está o jar da aplicação

![img_2.png](img_2.png)

# Arquivo jar da aplicação

[locacao-0.0.1-SNAPSHOT.jar](src%2Fmain%2Fjava%2Fcom%2Ffiap%2Flocacao%2Fdominio%2Fpacote%2Flocacao-0.0.1-SNAPSHOT.jar)

![img.png](img.png)

# Arquivo build maven

![img_4.png](img_4.png)

## Introdução:

Conforme foi solocitado para atender a demanda de um sistema gerencial de reservas de quartos (locação) foram desenvolvidos 5 APIS para este projeto: Endereço, localidade, predio, quarto, serviços e itens, reserva e pessoa. 

<h1 align="center">
  Desenvolvimento das APIs
</h1>

## Tecnologias

[GitLab](https://about.gitlab.com/):Plataforma de gerenciamento de ciclo de vida de desenvolvimento de software com versionamento de codigo git.

[GitLab](https://about.gitlab.com/):Plataforma de gerenciamento de ciclo de vida de desenvolvimento de software com versionamento de codigo git.
- [Spring Boot](https://spring.io/projects/spring-boot):Modulo derivado do Spring Framework que facilita desenvolvimento de aplicações java implementando injeção e inversão de dependencias
- [Postman](https://learning.postman.com/docs/developer/postman-api/intro-api/): Ferramenta destinada a desenvolvedores que possibilita testar chamadas API e gerar documentação de forma iterativa.Foi usado neste projeto para gerar collections e realizar teste de chamadas aos endpoints;
- [Tortoise](https://tortoisegit.org/docs/tortoisegit/): Ferramenta gerencial que facilita manipulação de projetos em GIT. Foi usado neste projeto para resolução de conflitos.
- [Sourcetree](https://confluence.atlassian.com/get-started-with-sourcetree): Assim como o Tortoise é uma ferramenta gerencial para facilitar o desenvolvimento de projetos em Git, no entanto possui uma interface mais receptivel e navegabilidade facilitada.Foi usado neste projeto paa navegação e criação de ramos.
## Práticas adotadas


- Uso de DTOs para a API
- Injeção de Dependências
- Arquitetura hexagonal
- Testes unitarios e Mocks

## Escalabilidade de sistema:

- [Modularização em Containner e Docker](https://about.gitlab.com/): Docker é uma plataforma de código aberto que facilita a criação, implantação e gerenciamento de aplicativos por meio de contêineres, que são ambientes isolados e leves. Esses contêineres empacotam aplicativos e suas dependências, permitindo uma execução consistente em diversos sistemas, eliminando problemas de compatibilidade e melhorando a eficiência no desenvolvimento.

```
version: '3'

services:
  ms-location-app: # Renomeado para ms-location-app
    image: openjdk:latest
    container_name: ms-location-app # Renomeado
    working_dir: /app
    volumes:
      - ./target:/app
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://ms-location-db:3306/ms_location?createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=America/Sao_Paulo # Corrigido o nome do serviço
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=
    depends_on:
      - ms-location-db # Renomeado
    command: ["java", "-jar", "/app/location-app.jar"]

  ms-location-db: # Renomeado para ms-location-db
    image: mysql:8
    container_name: ms-location-db # Renomeado
    environment:
      - MYSQL_ALLOW_EMPTY_PASSWORD=true
      - MYSQL_DATABASE=grupo44
    ports:
      - "3306:3306"


```
- [CI](https://about.gitlab.com/) (Continuous Integration):  CI (Continuous Integration) é uma prática de desenvolvimento em que as alterações de código são regularmente integradas e testadas automaticamente. O GitLab CI automatiza esse processo, organizando-o em pipelines, que representam as etapas de construção, teste e implantação de um aplicativo. Isso melhora a eficiência e a qualidade do desenvolvimento de software.[Veja pipeline executada](https://gitlab.com/mattec1/grupo-44-sistema-de-parquimetro-fiap/-/jobs/5473301001)

```
###############################################
##### Pipeline Gitlab-CI - v1.0           #####
##### MATTEC PROJETOS  - 13/03/2024       #####
##### GRUPO 44 LOCACAO HACKATHON FIAP     #####
###############################################


stages:
  - teste
  - build
  - deploy


executar_teste:
  stage: teste
  before_script:
    - echo "Preparando testes..."
    - chmod +x ./script.sh
  script:
    - ./script.sh
  after_script:
    - echo "Apagando arquivos temporários..."

executar_teste2:
  image: node:19.1
  needs:
    - executar_teste
  stage: teste
  script:
    - echo "Executando mais um teste..."
    - npm version

criar_imagens:
  stage: build
  script:
    - echo "Criando as imagens..."

push_imagens:
  needs:
    - criar_imagens
  stage: build
  script:
    echo "Realizando upload das imagens..."

kubernetes:
  stage: deploy
  script:
    - echo "Executando deploy..."


```


## Como Executar

### Localmente
- Clonar repositório git
- Construir o projeto:
```
./mvnw clean package
```
- Executar:


A API poderá ser acessada em [localhost:8080](http://localhost:8080)

O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)



<h1 align="center">
  API ENDEREÇO
</h1>

<p align="center">
 https://gitlab.com/mattec1/grupo-44-locacao-hackathon
</p>

Esta api deve ser a primeira a ser executada visto que os gestores a utlizarão para alimentar a base de conhecimento e dependencias para demais apis.
## API Endpoints

CADASTRO DE ENDEREÇOS

```
Requet:

curl --location 'http://localhost:8080/api/v1/enderecos/cadastrar' \
--header 'Content-Type: application/json' \
--data '{
  "rua": "Rua das Flores",
  "numero": 123,
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01234-567"
}'                           
```
* ATUALIZAÇÃo DE ENDEREÇOS:
```
Request:
curl --location --request PUT 'http://localhost:8080/api/v1/enderecos/atualizar/1' \
--header 'Content-Type: application/json' \
--data '{
  "id": 1,
  "rua": "Rua das Flores",
  "numero": 125,
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01234-567"
}'
```

LISTAR ENDEREÇOS

- GET /alocacao

```
http GET http://localhost:8080/api/v1/enderecos/buscar-todos

Request:

curl --location 'http://localhost:8080/api/v1/enderecos/buscar-todos' \
--data ''

Response:
{
    "data": [
        {
            "id": 1,
            "rua": "Rua das Flores",
            "numero": 123,
            "bairro": "Centro",
            "cidade": "São Paulo",
            "estado": "SP",
            "cep": "01234-567"
        }
    ],
    "paginator": {
        "pageNumber": 0,
        "totalElements": 1,
        "totalPages": 1
    }
}


```

* DELEÇÃO DE ENDEREÇOS:
```
Request:
curl --location --request DELETE 'http://localhost:8080/api/v1/enderecos/apagar/1' \
--data ''
```


<h1 align="center">
  API LOCALIDADES
</h1>

<p align="center">
 https://gitlab.com/mattec1/grupo-44-locacao-hackathon
</p>

Após alimentar a base com os possiveis endereços, o gestor deve cadastrar mais informações sobre a localidade
## API Endpoints


Lista de formas de pagamento

- CADASTRO DE LOCALIDADE

```
curl --location 'http://localhost:8080/api/v1/localidades' \
--header 'Content-Type: application/json' \
--data '{
  "id": 1,
  "nome": "Localidade Teste",
  "enderecoDTO": {
    "id": 1
  },
  "idsAmenidades": [1, 2, 3]
}
'
```

* ATUALIZAÇÃO
```
curl --location --request PUT 'http://localhost:8080/api/v1/localidades/1' \
--header 'Content-Type: application/json' \
--data '{
  "id": 1,
  "nome": "Localidade Teste update",
  "enderecoDTO": {
    "id": 1
  },
  "idsAmenidades": [1, 2, 3]
}
'
```

LISTA TODAS LOCALIDADES

```
curl --location 'http://localhost:8080/api/v1/localidades' \
--data ''                    
```
BUSCA LOCALIDADE POR ID:

```
curl --location 'http://localhost:8080/api/v1/localidades/1' \
--data ''                    
```
DELEÇÃO DE LOCALIDADE
```
curl --location --request DELETE 'http://localhost:8080/api/v1/localidades/1' \
--data ''
```
DELEÇÃO DE FORMA DE PAGAMENTO
- DELETE /formaPagto/{id}
```
DELETE http://localhost:8080/formaPagto/9
HTTP/1.1 204 No Content
Content-Length: 142
Content-Type: application/json

```
<h1 align="center">
  API PREDIOS
</h1>

<p align="center">
 https://gitlab.com/mattec1/grupo-44-sistema-de-parquimetro-fiap 
</p>


## API Endpoints

Agora os gestores devem cadastrar os predios pertencentes a cada localidade

CADASTRO DE PREDIOS
```
curl --location 'http://localhost:8080/api/v1/predio' \
--header 'Content-Type: application/json' \
--data '{
  "id": 1,
  "nome": "Prédio Teste",
  "localidade": {
    "id": 1
  }
}
'

```
ATUALIZAÇÃO DE PREDIOS
- POST /condutores
```
http POST http://localhost:8080/pescondutores

HTTP/1.1 200 OK
Content-Length: 129
Content-Type: application/json

{
  "nome": "João",
  "sobrenome": "Silva",
  "dataNascimento": "1990-01-15",
  "sexo": "M",
  "idade": 32,
  "email":"herickraposo97@gmail.com",
  "phone": "123-456-7890",
  "cell": "987-654-3210",
  "fotosUrls": "https://acesse.one/3otj2",
  "nat": "Brasil"
}
```

- GET /pessoas/{id}
```
http://localhost:8080/condutores/6
HTTP/1.1 200 OK
Content-Length: 129
Content-Type: application/json

{
    "id": 6,
    "nome": "João",
    "sobrenome": "Silva",
    "dataNascimento": "1990-01-15",
    "sexo": "M",
    "idade": 32,
    "email": "herickraposo97@gmail.com",
    "phone": "123-456-7890",
    "cell": "987-654-3210",
    "fotosUrls": "https://acesse.one/3otj2",
    "nat": "Brasil",
    "veiculos": [
        {
            "id": 8,
            "marca": "HYUNDAI",
            "modelo": "HB20",
            "matricula": "PUN2515",
            "cavalos": 300
        }
    ]
}           
```

- PUT /pesscondutoresoas/3
```
tp://localhost:8080/condutores/3
HTTP/1.1 200 OK
Content-Type: application/json
transfer-encoding: chunked

{
    "id": 3,
    "nome": "João",
    "sobrenome": "Silva",
    "dataNascimento": "1990-01-15",
    "sexo": "M",
    "idade": 32,
    "email": "herickraposo97@gmail.com",
    "phone": "123-456-7890",
    "cell": "987-654-3210",
    "fotosUrls": "https://acesse.one/3otj2",
    "nat": "Brasil",
}
```
Alteração de campo único por exemplo:
- PATCH /condutores/{id}
```
PATCH http://localhost:8080/condutores/1
HTTP/1.1 200 OK
Content-Length: 142
Content-Type: application/json

{

   "sobrenome": "Rancho fundo Mauá"
           
}
 Retorna a alteração 
{
    "id": 1,
    "nome": "Fernanda",
    "sobrenome": "Rancho fundo Mauá",
    "dataNascimento": "1971-01-21",
    "sexo": "F"
}
```

Deletando uma pessoa:
- DELETE /condutores/{id}
```
DELETE http://localhost:8080/condutores/1
HTTP/1.1 204 No Content
Content-Length: 142
Content-Type: application/json

```

<h1 align="center">
  API VEICULO
</h1>

<p align="center">
 https://gitlab.com/mattec1/grupo-44-sistema-de-parquimetro-fiap 
</p>

API para gerenciamento de veiculos. Ao consumir esta api o desenvolvedor conseguirá realizar a criação, leitura,atualização,leitura e deleção(CRUD) dos registros de veiculos.

## API Endpoints



Testes unitãrios: 

```
MockHttpServletRequest:
      HTTP Method = GET
      Request URI = /clientes/4
       Parameters = {}
          Headers = []
             Body = <no character encoding set>
    Session Attrs = {}

Handler:
             Type = com.fiap.locacao.dominio.gestao.cliente.controllers.ClienteController
           Method = com.fiap.locacao.dominio.gestao.cliente.controllers.ClienteController#getProductById(Long)

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 200
    Error message = null
          Headers = [Content-Type:"application/json"]
     Content type = application/json
             Body = {"id":-1599008400270023032,"paisOrigem":null,"cpf":null,"passaPorte":null,"nomeCompleto":"usuario","dataNascimento":null,"telefone":null,"email":"usuariojava.util.Random@58324c9f@exemple.com"}
    Forwarded URL = null
   Redirected URL = null
          Cookies = []

Process finished with exit code 0



```


![img_1.png](img_1.png)
