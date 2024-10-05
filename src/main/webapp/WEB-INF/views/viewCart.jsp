<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Include your existing head content -->
    <meta charset="UTF-8">
    <title>Mini Shop - Your Cart</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
    <!-- Include the same navbar as in your other pages -->
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

    <!-- Cart Contents -->
    <div class="container my-5">
        <h2>Your Shopping Cart</h2>
        <c:if test="${empty itemsList}">
            <p>Your cart is empty.</p>
            <a href="productList.jsp" class="btn btn-primary">Continue Shopping</a>
        </c:if>
        <c:if test="${not empty itemsList}">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Product Image</th>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${itemsList}">
                        <tr>
                            <td>
                                <img src="${item.product.imageURL}" alt="${item.product.name}" width="100" />
                            </td>
                            <td>${item.product.name}</td>
                            <td>$${item.product.price}</td>
                            <td>${item.quantity}</td>
                            <td>$${item.product.price * item.quantity}</td>
                            <td>
                                <!-- Remove Item Button -->
                                <form action="removeFromCart" method="post" class="d-inline">
                                    <input type="hidden" name="productId" value="${item.product.id}" />
                                    <button type="submit" class="btn btn-danger btn-sm">
                                        Remove
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="4" class="text-end"><strong>Total:</strong></td>
                        <td colspan="2"><strong>$${cart.totalPrice}</strong></td>
                    </tr>
                </tbody>
            </table>
            <a href="checkout" class="btn btn-success">Proceed to Checkout</a>
            <a href="productList.jsp" class="btn btn-primary">Continue Shopping</a>
            <form action="OrderPlace" method="post">
    <button type="submit" class="btn btn-success">Place Order</button>
</form>
        </c:if>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
