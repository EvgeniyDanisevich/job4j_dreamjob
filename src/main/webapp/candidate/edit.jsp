<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.store.PsqlCandidateStore" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script>
        $(document).ready(function () {
            getCities();
            function getCities() {
                $.ajax({
                    type: 'GET',
                    url: 'http://localhost:8080/dreamjob/city',
                    dataType: 'json'
                }).done(function(data) {
                    let count = data.length;
                    let cityID = $('#city-id').text();
                    for (let i = 0; i < count; i++) {
                        let city = data[i];
                        let option = new Option(city.name, city.id);
                        $(option).html(city.name);
                        $('#city-control').append(option);
                    }
                }).fail(function(err) {
                });
            }
        });
    </script>

    <title>???????????? ??????????</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate can = new Candidate(0, "", "", 0);
    if (id != null) {
        can = PsqlCandidateStore.instOf().findById(Integer.parseInt(id));
    }
%>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                ?????????? ????????????????.
                <% } else { %>
                ???????????????????????????? ??????????????????.
                <% } %>
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidates.do?id=<%=can.getId()%>" method="post">
                    <div class="form-group">
                        <label>??????</label>
                        <input type="text" class="form-control" name="name" value="<%=can.getName()%>">
                    </div>
                    <div class="form-group">
                        <label>??????????</label>
                        <% if (can.getCityId() == 0) { %>
                        <p id="city-id" hidden>0</p>
                        <% } else { %>
                        <p id="city-id" hidden><%=can.getCityId()%></p>
                        <% } %>
                        <select id="city-control" name="city" class="form-control" required>

                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">??????????????????</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>