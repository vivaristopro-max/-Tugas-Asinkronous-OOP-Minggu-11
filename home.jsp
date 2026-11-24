
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Home - Katalog Produk</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f9;
            text-align: center;
            padding-top: 100px;
        }

        .container {
            background: white;
            width: 500px;
            margin: auto;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 3px 12px #ccc;
        }

        a {
            display: inline-block;
            padding: 12px 20px;
            margin-top: 20px;
            background: #2563eb;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>Katalog Produk</h1>
        <p>Selamat datang di aplikasi katalog produk sederhana.</p>

        <a href="<%= request.getContextPath() %>/product">
            Lihat Daftar Produk
        </a>

        <br>

        <a href="<%= request.getContextPath() %>/product?action=add">
            Tambah Produk
        </a>
    </div>

</body>
</html>
