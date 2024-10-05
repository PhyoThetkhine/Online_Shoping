<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Product Details at Mini Shop" />
    <meta name="author" content="Your Name" />
    <title>Mini Shop - Product Details</title>

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
        .footer {
            background-color: #343a40; /* Dark background for footer */
            color: white;
            margin-top: auto;
        }
        .card-img-top {
            max-height: 400px;
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
    <!-- Product Details Section -->
    <section class="py-5">
        <div class="container px-4 px-lg-5">
            <!-- Optional Flash Message -->
            <c:if test="${not empty message}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <div class="row gx-4 gx-lg-5">
                <!-- Product Image -->
                <div class="col-md-6">
                    <img class="img-fluid rounded" src="${product.imageURL}" alt="${product.name}">
                </div>
                
                <!-- Product Details -->
                <div class="col-md-6">
                    <h2>${product.name}</h2>
                    <h4 class="text-muted">$${product.price}</h4>
                    <p>${product.description}</p>
                    <p><strong>Category:</strong> ${product.category.name}</p>
                    <p><strong>Available Quantity:</strong> ${product.quantity}</p>
                    
                    <!-- Add to Cart Button -->
                    <form action="addToCart" method="post" class="d-inline">
                        <input type="hidden" name="productId" value="${product.id}" />
                        <button type="submit" class="btn btn-primary me-2">
                            <i class="bi bi-cart-fill me-1"></i> Add to Cart
                        </button>
                    </form>
                    
                   <form action="order" method="post" class="d-inline">
                        <input type="hidden" name="productId" value="${product.id}" />
                        <button type="submit" class="btn btn-success">
                            <i class="bi bi-bag-fill me-1"></i> Order
                        </button>
                    </form>
                </div>
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
