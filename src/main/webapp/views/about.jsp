<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base/base.jsp"%>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
<c:url value="https://i.pinimg.com/736x/78/55/28/785528e8a290591a835abfe06a2508b6--berserk-manga-berserk-guts.jpg" var="imageUrl"/>
<section id="about-section" class="pt-5 pb-5">
    <div class="container wrapabout">
        <div class="red"></div>
        <div class="row">
            <div class="col-lg-6 align-items-center justify-content-left d-flex mb-5 mb-lg-0">
                <div class="blockabout">
                    <div class="blockabout-inner text-center text-sm-start">
                        <div class="title-big pb-3 mb-3">
                            <h3><fmt:message key="about.title"/></h3>
                        </div>
                        <p class="description-p text-muted pe-0 pe-lg-0">
                            Голубые волосы развевает ветер (Ага) <br>
                            Я качу на такой скорости, они и не заметят (Нет) <br>
                            Доболтала с первым, и второй мне позвонил <br>
                            Говорю каждому, то что он такой один (Хи-хи) <br>
                        </p>
                        <p class="description-p text-muted pe-0 pe-lg-0">
                            Мэйби-Мэйби Бэйби — маленькая стерва <br>
                            В мини Дисней Ленде главная принцесса <br>
                            Она смотрит и хочет такие же косички (Мои) <br>
                            Но не знает, что мне их заплетает её бывший <br>

                        </p>
                        <p class="description-p text-muted pe-0 pe-lg-0">
                            Мэйби Бэйби, Мэйби-Мэйби Бэйби <br>
                            Лучше твоей детки, лучше всех на свете <br>
                            Губы пахнут малиной, девочка-аскорбинка <br>
                            Мальчики, соберитесь, это так некрасиво <br>
                            Мэйби Бэйби, Мэйби-Мэйби Бэйби <br>
                            Лучше твоей детки, лучше всех на свете <br>
                            Губы пахнут малиной, девочка-аскорбинка <br>
                            Мальчики, соберитесь, это так некрасиво <br>

                        </p>
                        <div class="sosmed-horizontal pt-3 pb-3">
                            <a href="https://www.facebook.com/gretathunbergsweden/"><i class="fa fa-facebook fa-lg"></i></a>
                            <a href="https://www.instagram.com/lilpeep/"><i class="fa fa-instagram fa-lg"></i></a>
                            <a href="https://t.me/prokopme"><i class="fa fa-telegram fa-lg"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-6 mt-5 mt-lg-0">
                <figure class="potoaboutwrap">
                    <img alt="photoabout" src="${imageUrl}" class="img-fluid fixed-image" type="image/jpg">
                </figure>
            </div>
        </div>
    </div>
</section>
<%@include file="base/footer.jsp"%>
<style>
    .fixed-image {
        width: 400px;
        height: 700px;
    }
</style>
</body>
</html>
