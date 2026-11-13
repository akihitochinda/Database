<?xml version="1.0" encoding="UTF-8"?>
<!--
    3. Map each CompanyName to an array of all its Restaurant Names.
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
                <th>RestaurantNames</th>
            </tr>
            <xsl:for-each select="Companies/Company">
                <xsl:variable name="companyName" select="CompanyName"></xsl:variable>
                <tr>
                    <td>
                        <xsl:value-of select="CompanyName"></xsl:value-of>
                    </td>
                    <td>
                        <!--
                            Alternatively use the absolute path, such as
                            "/RestaurantApplication/Restaurants/Restaurant[CompanyName/text()=$companyName]/Name"
                        -->
                        <!--
                            Add attribute separator="," to define a different separator from default (space).
                        -->
                        <xsl:value-of select="../../Restaurants/Restaurant[CompanyName/text()=$companyName]/Name" separator=", ">
                        </xsl:value-of>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
        </body></html>
    </xsl:template>
</xsl:stylesheet>