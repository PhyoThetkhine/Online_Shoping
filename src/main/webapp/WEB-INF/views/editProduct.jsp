<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Product</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Edit Product</h1>

        <form action="updateProduct" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${product.id}" />

            <div class="form-group">
                <label for="name">Product Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${product.name}" required>
            </div>

            <div class="form-group">
                <label for="description">Description</label>
                <textarea class="form-control" id="description" name="description" rows="3" required>${product.description}</textarea>
            </div>

            <div class="form-group">
                <label for="price">Price</label>
                <input type="number" step="0.01" class="form-control" id="price" name="price" value="${product.price}" required>
            </div>

            <div class="form-group">
                <label for="quantity">Quantity</label>
                <input type="number" class="form-control" id="quantity" name="quantity" value="${product.quantity}" required>
            </div>

            <div class="form-group">
                <label for="imageURL">Current Image</label>
                <div>
                <input type="hidden" name="existingImageURL" value="${product.imageURL}">
                   <div>
                    <img src="${product.imageURL}" alt="${product.name}" width="150">
                </div>
                </div>
            </div>

            <div class="form-group">
                 <label for="imageFile">Upload New Image (optional):</label>
   				 <input type="file" id="imageFile" name="imageFile">

            </div>

            <div class="form-group">
                <label for="category">Category:</label>
                <select id="category" name="category.id" class="form-control" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Update Product</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
