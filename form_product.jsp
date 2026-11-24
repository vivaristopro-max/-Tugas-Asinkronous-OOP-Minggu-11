
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Product"%>

<%!
    private String escapeHTML(String value) {
        if (value == null) return "";

        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
%>

<%
    Product p = (Product) request.getAttribute("product");
    String error = (String) request.getAttribute("error");

    boolean editing = (p != null && p.getId() > 0);

    String nameValue = request.getParameter("name");
    if (nameValue == null) {
        nameValue = p != null ? p.getName() : "";
    }

    String categoryValue = request.getParameter("category");
    if (categoryValue == null) {
        categoryValue = p != null ? p.getCategory() : "";
    }

    String priceValue = request.getParameter("price");
    if (priceValue == null) {
        priceValue = p != null ? String.valueOf(p.getPrice()) : "";
    }

    String stockValue = request.getParameter("stock");
    if (stockValue == null) {
        stockValue = p != null ? String.valueOf(p.getStock()) : "";
    }

    String descriptionValue = request.getParameter("description");
    if (descriptionValue == null) {
        descriptionValue = p != null && p.getDescription() != null
                ? p.getDescription() : "";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form Produk</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f3f4f6;
            margin: 0;
            padding: 40px 20px;
        }

        .container {
            max-width: 540px;
            margin: auto;
            background: white;
            padding: 28px;
            border-radius: 12px;
        }

        h1 {
            margin-top: 10px;
            margin-bottom: 22px;
        }

        label {
            display: block;
            margin-top: 15px;
            margin-bottom: 8px;
        }

        input, textarea {
            width: 100%;
            padding: 12px;
            box-sizing: border-box;
            border: 1px solid #aaa;
            border-radius: 3px;
            font-size: 14px;
        }

        textarea {
            height: 90px;
            resize: vertical;
        }

        .error {
            background: #fee2e2;
            color: #b91c1c;
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 18px;
        }

        button {
            margin-top: 20px;
            padding: 13px 22px;
            background: #2563eb;
            color: white;
            border: none;
            cursor: pointer;
        }

        .cancel {
            margin-left: 10px;
            color: #2563eb;
        }
    </style>
</head>

<body>

<div class="container">

    <h1><%= editing ? "Edit Produk" : "Tambah Produk" %></h1>

    <% if (error != null) { %>
        <div class="error">
            <strong>Validasi Gagal!</strong><br>
            <%= escapeHTML(error) %>
        </div>
    <% } %>

    <form action="<%= request.getContextPath() %>/product"
          method="POST"
          novalidate>

        <input type="hidden"
               name="action"
               value="<%= editing ? "edit" : "add" %>">

        <% if (editing) { %>
            <input type="hidden"
                   name="id"
                   value="<%= p.getId() %>">
        <% } %>

        <label>Nama Produk</label>
        <input type="text"
               name="name"
               value="<%= escapeHTML(nameValue) %>">

        <label>Kategori</label>
        <input type="text"
               name="category"
               value="<%= escapeHTML(categoryValue) %>">

        <label>Harga</label>
        <input type="number"
               step="any"
               name="price"
               value="<%= escapeHTML(priceValue) %>">

        <label>Stok</label>
        <input type="number"
               step="1"
               name="stock"
               value="<%= escapeHTML(stockValue) %>">

        <label>Deskripsi</label>
        <textarea name="description"><%= escapeHTML(descriptionValue) %></textarea>

        <button type="submit">
            Simpan Produk
        </button>

        <a class="cancel"
           href="<%= request.getContextPath() %>/product">
            Batal
        </a>

    </form>

</div>

</body>
</html>
