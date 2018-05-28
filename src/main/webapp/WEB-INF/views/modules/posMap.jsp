<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
</head>
<body>
    <table style="width:100%; height:100%;">
      <tbody>
        <tr>
          <td align="center" id="loading">
            <img src="${ctx}/images/loading.gif" alt="Loading..." title="Loading..."/>
          </td>
        </tr>
      </tbody>
    </table>
    <script type="text/javascript">
      adfdojo.addOnLoad( function() {
        var url = "posList.jsf";
        try {
          var b = adfdojo.contentBox(adfdojo.body());
          url += "?width=" + (b.w) + "&height=" + (b.h);
        }
        catch (exception) {
          console.error("Unable to determine dimensions of body element.");
        }
        window.location.href = url;
      });
    </script>
</body>
</html>