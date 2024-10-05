<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Shop for the best products at Mini Shop" />
    <meta name="author" content="Your Name" />
    <title>Mini Shop - Home</title>

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
    <!-- Custom CSS -->
    <style>
        body {
            font-family: 'Arial', sans-serif;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        .navbar {
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .header {
            background-color: #343a40; /* Dark background for header */
            color: white;
            padding: 60px 0;
        }
        .product-card {
            transition: transform 0.2s;
        }
        .product-card:hover {
            transform: scale(1.05); /* Slightly enlarge on hover */
        }
        .footer {
            background-color: #343a40; /* Dark background for footer */
            color: white;
            margin-top: auto;
        }
        .card-img-top {
            height: 200px;
            object-fit: cover;
        }
    </style>
</head>
<body>
    <!-- Navigation-->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container px-4 px-lg-5">
        <a class="navbar-brand" href="Index.jsp">Mini Shop</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" 
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                <li class="nav-item"><a class="nav-link active" aria-current="page" href="Index.jsp">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="productList.jsp">Products</a></li>
                <li class="nav-item"><a class="nav-link" href="#!">About</a></li>
            </ul>
            <form class="d-flex">
                <a class="btn btn-outline-dark" href="viewCart">
                    <i class="bi-cart-fill me-1"></i>
                    Cart
                    <span class="badge bg-dark text-white ms-1 rounded-pill">
                        ${sessionScope.cart != null ? sessionScope.cart.totalItemCount : 0}
                    </span>
                </a>
            </form>
        </div>
    </div>
</nav>

    <!-- Header-->
    <header class="header text-center">
        <div class="container px-4 px-lg-5 my-5">
            <h1 class="display-4 fw-bolder">Shop in Style</h1>
            <p class="lead fw-normal text-white-50 mb-0">Discover our exclusive range of products</p>
        </div>
    </header>

    <!-- Section: Dynamic Product Listing -->
    <section class="py-5">
        <div class="container px-4 px-lg-5 mt-5">
            <!-- Optional Flash Message -->
            <c:if test="${not empty message}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            
            <div class="row gx-4 gx-lg-5 row-cols-1 row-cols-md-3 row-cols-xl-4 justify-content-center">
                <c:forEach var="product" items="${products}">
                    <div class="col mb-5">
                        <div class="card h-100 product-card">
                            <!-- Product image -->
                            <img class="card-img-top" src="${product.imageURL}" alt="${product.name}" />

                            <!-- Product details -->
                            <div class="card-body p-4">
                                <div class="text-center">
                                    <!-- Product name -->
                                    <h5 class="fw-bolder">${product.name}</h5>
                                    <!-- Product price -->
                                    $${product.price}
                                </div>
                            </div>

                            <!-- Product actions -->
                            <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                                <div class="text-center">
                                    <a class="btn btn-outline-dark mt-auto" href="productDetails?id=${product.id}">
                                        <i class="bi bi-eye-fill"></i> View Details
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${empty products}">
                    <div class="col-12 text-center">
                        <p class="text-muted">No products available at the moment.</p>
                    </div>
                </c:if>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="footer py-4">
        <div class="container">
            <p class="m-0 text-center">© Mini Shop 2024</p>
        </div>
    </footer>

    <!-- Bootstrap 5 JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
