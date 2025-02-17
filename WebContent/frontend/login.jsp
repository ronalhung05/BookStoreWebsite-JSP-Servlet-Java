<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="page_head.jsp">
    <jsp:param name="pageTitle" value="Customer Login"/>
</jsp:include>
    <style>
        .btn-custom {
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
<body>
<main>
    <!-- section -->
    <section class="my-lg-14 my-8">
        <form action="login" method="post" class="row g-3 needs-validation" novalidate>
            <div class="container">
                <!-- row -->
                <div class="row justify-content-center align-items-center">
                    <div class="col-12 col-md-6 col-lg-4 order-lg-1 order-2">
                        <!-- img -->
                        <img
                                src="images/svg-graphics/signin-g.svg"
                                alt=""
                                class="img-fluid"
                        />
                    </div>
                    <!-- col -->
                    <div
                            class="col-12 col-md-6 offset-lg-1 col-lg-4 order-lg-2 order-1"
                    >
                        <div class="mb-lg-9 mb-5">
                            <h1 class="mb-1 h2 fw-bold">Sign in to Evergreen BookStore</h1>
                            <p>
                                Welcome back to Evergreen BookStore! Enter your email to get started.
                            </p>
                        </div>

                        <form class="row g-3 needs-validation" novalidate >
                            <div class="row g-3">
                                <div class="col-12">
                                    <label for="email" class="form-label visually-hidden">Email address</label>
                                    <input
                                            id="email"
                                            type="email"
                                            class="form-control"
                                            name="email"
                                            placeholder="Email"
                                            required
                                    />
                                    <div class="invalid-feedback">Please enter email.</div>
                                </div>
                                <div class="col-12">
                                    <!-- input -->
                                    <div class="password-field position-relative">
                                        <label for="password" class="form-label visually-hidden">Password</label>
                                        <input
                                                id="password"
                                                type="password"
                                                name="password"
                                                placeholder="Enter Password"
                                                class="form-control"
                                                required
                                        />
                                        <span
                                        ><i id="passwordToggler" class="bi bi-eye-slash"></i
                                        ></span>
                                        <div class="invalid-feedback">Please enter password.</div>
                                    </div>
                                </div>

                                <div class="h-captcha" data-sitekey="ca61f481-d935-460c-aa2f-61f113c2fa54"></div>

                                <div class="col-12 d-grid">
                                    <button type="submit" class="btn btn-primary btn-custom">
                                        Sign In
                                    </button>
                                </div>
                                <div>
                                    Don’t have an account? <a href="register"> Sign Up</a>
                                </div>
                                <div class="col-12 d-grid mt-2">
                                    <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:9090/BookStoreWebsite/login&response_type=code&client_id=1070286133860-jksqqq9c541t700aseje19s2t12sb462.apps.googleusercontent.com&approval_prompt=force" class="btn btn-lg btn-danger btn-custom">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="16" fill="currentColor" class="bi bi-google" viewBox="0 0 16 16">
                                            <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z"/>
                                        </svg>
                                        <span class="ms-2 fs-6">Sign in with Google</span>
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </form>
    </section>
</main>
<script src="../js/vendors/validation.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<script>
    $(document).ready(function() {
        <c:if test="${message != null}">
        toastr.options = {
            "closeButton": true,
            "debug": false,
            "newestOnTop": false,
            "progressBar": true,
            "positionClass": "toast-top-right",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "300",
            "hideDuration": "1000",
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        };
        toastr.warning("${message}");
        </c:if>
    });
</script>
</body>
</html>
