<%@ include file="include/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Select the modules you want to be quizzed on</h1>

<!-- This needs more work -->

<form method="post" action="Servlet">
    <input type="hidden" name="action" value="SELECT_QUIZ">
    <ul>
        <c:forEach var="subject" items="${selection.subjects}">
            <li>${subject.name}</li>
            <ul>
                <c:forEach var="category" items="${subject.categories}">
                    <li>${category.name}</li>
                    <ul>
                        <c:forEach var="module"
                            items="${category.modules}">
                            <li><input type="checkbox"
                                name="module" value=${module.id}>${module.name}</li>
                        </c:forEach>
                    </ul>
                </c:forEach>
            </ul>
        </c:forEach>
    </ul>
    <br /> <input type="submit" value="Submit">
</form>

<%@ include file="include/footer.html"%>