<?xml version="1.0" encoding="UTF-8"?>
<!--
    9. For each Review, display the Review’s RestaurantId, UserName, Rating, and the count of the UserName’s Recommendations for the RestaurantId.
-->
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/RestaurantApplication">
        <html><head><title>Reviews with Recommendations</title></head><body>
        <table border="1">
            <tr>
                <th>RestaurantId</th>
                <th>UserName</th>
                <th>Rating</th>
                <th>RecommendationCount</th>
            </tr>
            <xsl:for-each select="Reviews/Review">
                <xsl:variable name="restaurantId" select="RestaurantId"></xsl:variable>
                <xsl:variable name="userName" select="UserName"></xsl:variable>
                <tr>
                    <td>
                        <xsl:value-of select="$restaurantId"></xsl:value-of>
                    </td>
                    <td>
                        <xsl:value-of select="$userName"></xsl:value-of>
                    </td>
                    <td>
                        <xsl:value-of select="Rating"></xsl:value-of>
                    </td>
                    <td>
                        <xsl:value-of select="count(../../Recommendations/Recommendation[UserName=$userName and RestaurantId=$restaurantId])">
                        </xsl:value-of>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
        </body></html>
    </xsl:template>
</xsl:stylesheet>