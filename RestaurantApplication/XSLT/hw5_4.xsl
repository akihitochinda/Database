<?xml version="1.0" encoding="UTF-8"?>
<!--
    4. Now “unroll” the array of Restaurant Names so each row contains exactly one CompanyName and Restaurant Name.
-->
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/RestaurantApplication">
        <html><head><title>Company and Restaurants</title></head><body>
        <table border="1">
            <tr>
                <th>CompanyName</th>
                <th>RestaurantName</th>
            </tr>
            <xsl:for-each select="Restaurants/Restaurant">
                <tr>
                    <td>
                        <xsl:value-of select="CompanyName"></xsl:value-of>
                    </td>
                    <td>
                        <xsl:value-of select="Name"></xsl:value-of>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
        </body></html>
    </xsl:template>
</xsl:stylesheet>