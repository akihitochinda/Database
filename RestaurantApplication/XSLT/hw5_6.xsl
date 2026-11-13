<?xml version="1.0" encoding="UTF-8"?>
<!--
    6. Determine the count of Recommendations made by “alice1".
-->
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/RestaurantApplication">
        <html><head><title>alice1 Recommendations</title></head><body>
        <table border="1">
            <tr>
                <th>UserName</th>
                <th>RecommendationCount</th>
            </tr>
            <tr>
                <td>
                    alice1
                </td>
                <td>
                    <xsl:value-of select="count(Recommendations/Recommendation[UserName='alice1'])">
                    </xsl:value-of>
                </td>
            </tr>
        </table>
        </body></html>
    </xsl:template>
</xsl:stylesheet>