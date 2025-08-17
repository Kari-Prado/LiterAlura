# 📚 LiterAlura

Aplicação Java com Spring Boot desenvolvida para o desafio da **Alura**.  
O projeto consome a API **Gutendex** ([https://gutendex.com/books/](https://gutendex.com/books/)), armazena livros e autores em um banco de dados **PostgreSQL**, e permite buscas e listagens interativas via console.

---

## 🚀 Tecnologias utilizadas
- **Java 17+**
- **Spring Boot 3.2.3**
- **Maven 4**
- **Spring Data JPA**
- **PostgreSQL**
- **Jackson** (para conversão JSON → Objetos Java)

---

## 📑 Funcionalidades

A aplicação **LiterAlura** oferece as seguintes funcionalidades:

### 📚 Livros
- 🔍 **Buscar livro por título** na API Gutendex e salvar no banco de dados.  
- 📜 **Listar todos os livros** já buscados.  
- 🌍 **Filtrar livros por idioma**. 
- 📊 **Exibir estatísticas de livros por idioma**, mostrando a quantidade registrada no banco.  

### 👤 Autores
- 🖊️ **Listar todos os autores** cadastrados no sistema.  
- ⏳ **Listar autores vivos em um determinado ano**, utilizando *derived queries* do Spring Data JPA.  

### ⚠️ Tratamento de erros
- Mensagens claras quando um livro não é encontrado na API.  
- Avisos para entradas inválidas fornecidas pelo usuário no menu.  
- Validação para evitar duplicação de livros já cadastrados.

---

## 👩‍💻 Autoria

- Projeto desenvolvido por **Vitoria Prado**.  
- Criado como parte do desafio **Alura + Oracle Next Education**.  
- Objetivo: praticar integração com APIs REST, persistência com Spring Data JPA e manipulação de JSON com Jackson.  
- Todas as funcionalidades foram implementadas visando aprendizado prático e modularidade do código.
