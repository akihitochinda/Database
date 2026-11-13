<?xml version="1.0" encoding="UTF-8"?>
<!--
    10. For each Review, display the Restaurant Name, User’s FirstName, and Review Rating.
-->
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/RestaurantApplication">
        <html><head><title>Rviews with Details</title></head><body>
        <table border="1">
            <tr>
                <th>RestaurantName</th>
                <th>FirstName</th>
                <th>Rating</th>
            </tr>
            <xsl:for-each select="Reviews/Review">
                <xsl:variable name="restaurantId" select="RestaurantId"></xsl:variable>
                <xsl:variable name="userName" select="UserName"></xsl:variable>
                <tr>
                    <td>
                        <xsl:value-of select="../../Restaurants/Restaurant[RestaurantId=$restaurantId]/Name">
                        </xsl:value-of>
                    </td>
                    <td>
                        <xsl:value-of select="../../Users/User[UserName=$userName]/FirstName">
                        </xsl:value-of>
                    </td>
                    <td>
                        <xsl:value-of select="Rating"></xsl:value-of>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
        </body></html>
    </xsl:template>
</xsl:stylesheet>