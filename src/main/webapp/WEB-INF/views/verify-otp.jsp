<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verify OTP</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h2 class="text-center mb-4">Verify OTP</h2>
            <p>${message}</p>
            <form action="verifyOtp" method="post" class="needs-validation" novalidate>
                <div class="mb-3">
                    <label for="otp" class="form-label">Enter OTP:</label>
                    <input type="text" id="otp" name="otp" class="form-control" required />
                    <div class="invalid-feedback">Please enter the OTP sent to your email.</div>
                </div>
                <button type="submit" class="btn btn-primary w-100">Verify</button>
            </form>

            <!-- Display error message -->
            <c:if test="${not empty errorOtp}">
                <div class="alert alert-danger mt-3">${errorOtp}</div>
            </c:if>
        </div>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
