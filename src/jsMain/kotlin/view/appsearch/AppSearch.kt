package view.appsearch

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import materialui.styles.StylesSet
import materialui.styles.childWithStyles
import materialui.styles.muitheme.shadows
import materialui.styles.palette.*
import materialui.styles.transitions.create
import materialui.styles.typography.fontWeightMedium
import materialui.styles.typography.fontWeightRegular
import materialui.styles.typography.typography
import react.RBuilder

fun RBuilder.appSearch(handler: RBuilder.() -> Unit) = childWithStyles(
    AppSearchComponent::class,
    appSearchStyles,
    handler = handler
)

private val appSearchStyles: StylesSet.() -> Unit = {
    "@global" {
        ".algolia-autocomplete" {
            descendants(".ds-dropdown-menu") {
                boxShadow = theme.shadows[1]
                borderRadius = theme.shape.borderRadius.px
                before {
                    display = Display.none
                }
                descendants("[class^=ds-dataset-]") {
                    border = "0"
                    borderRadius = theme.shape.borderRadius.px
                    backgroundColor = theme.palette.background.paper
                }
            }
            descendants(".algolia-docsearch-suggestion--category-header-lvl0") {
                color = theme.palette.text.primary
            }
            descendants(".algolia-docsearch-suggestion .algolia-docsearch-suggestion--subcategory-column") {
                opacity = 1
                padding(5.33.px, 10.66.px)
                textAlign = TextAlign.right
                width = 30.pct
            }
            descendants(".algolia-docsearch-suggestion .algolia-docsearch-suggestion--content") {
                float = Float.right
                padding(5.33.px, 0.px, 5.33.px, 10.66.px)
                width = 70.pct
            }
            descendants(".algolia-docsearch-suggestion--subcategory-column-text") {
                color = theme.palette.text.secondary
                fontWeight = theme.typography.fontWeightRegular
            }
            descendants(".algolia-docsearch-suggestion--highlight") {
                color = if (theme.palette.type == PaletteType.light) Color("#174d8c") else Color("#acccf1")
            }
            descendants(".algolia-docsearch-suggestion") {
                textDecoration = TextDecoration.none
                backgroundColor = theme.palette.background.paper
            }
            descendants(".algolia-docsearch-suggestion--title") {
                typography(theme.typography.h6)
            }
            descendants(".algolia-docsearch-suggestion--text") {
                typography(theme.typography.body2)
            }
            "&& .algolia-docsearch-suggestion--no-results" {
                width = 100.pct
                before {
                    display = Display.none
                }
            }
            descendants("b") {
                fontWeight = theme.typography.fontWeightMedium
            }
        }
    }
    "root" {
        fontFamily = theme.typography.fontFamily
        position = Position.relative
        marginRight = (theme.spacing.unit * 2).px
        marginLeft = theme.spacing.unit.px
        borderRadius = theme.shape.borderRadius.px
        backgroundColor = theme.palette.common.white.withAlpha(0.15)
        hover {
            backgroundColor = theme.palette.common.white.withAlpha(0.25)
        }
        descendants("\$inputInput") {
            transition = theme.transitions.create("width")
            width = 120.px
            focus {
                width = 170.px
            }
        }
    }
    "search" {
        width = (theme.spacing.unit * 9).px
        height = 100.pct
        position = Position.absolute
        pointerEvents = PointerEvents.none
        display = Display.flex
        alignItems = Align.center
        justifyContent = JustifyContent.center
    }
    "inputRoot" {
        color = Color.inherit
    }
    "inputInput" {
        padding(
            theme.spacing.unit.px,
            theme.spacing.unit.px,
            theme.spacing.unit.px,
            (theme.spacing.unit * 9).px
        )
    }
}