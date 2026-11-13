<?xml version="1.0" encoding="UTF-8"?>
<!--
    7. Map each Restaurant Name to a count of their Recommendations.
-->
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/RestaurantApplication">
        <html><head><title>Restaurants and Recommendations</title></head><body>
        <table border="1">
            <tr>
                <th>RestaurantName</th>
                <th>RecommendationCount</th>
            </tr>
            <xsl:for-each select="Restaurants/Restaurant">
                <xsl:variable name="restaurantId" select="RestaurantId"></xsl:variable>
                <tr>
                    <td>
                        <xsl:value-of select="Name"></xsl:value-of>
                    </td>
                    <td>
                        <xsl:value-of select="count(../../Recommendations/Recommendation[RestaurantId/text()=$restaurantId])">
                        </xsl:value-of>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
        </body></html>
    </xsl:template>
</xsl:stylesheet>