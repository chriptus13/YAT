package pt.isel.daw.daw_g06.configs

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.util.UriTemplate
import pt.isel.daw.daw_g06.interceptors.AuthenticationInterceptor
import pt.isel.daw.daw_g06.interceptors.BearerAuthenticationInterceptor
import pt.isel.daw.daw_g06.messageConverters.HALMessageConverter
import pt.isel.daw.daw_g06.messageConverters.SirenMessageConverter

object Uri {
    const val HOME = "/"

    // Project paths

    const val PROJECTS = "/projects"
    const val PROJECT = "/projects/{pid}"
    const val PROJECT_NAME = "/projects/{pid}/name"
    const val PROJECT_DESCRIPTION = "/projects/{pid}/description"
    const val PROJECT_INITIAL_STATE = "/projects/{pid}/initialState"

    // Issue paths

    const val PROJECT_ISSUES = "/projects/{pid}/issues"
    const val PROJECT_ISSUE = "/projects/{pid}/issues/{iid}"
    const val PROJECT_ISSUE_NAME = "/projects/{pid}/issues/{iid}/name"
    const val PROJECT_ISSUE_DESCRIPTION = "/projects/{pid}/issues/{iid}/description"
    const val PROJECT_ISSUE_STATE = "/projects/{pid}/issues/{iid}/state"

    // Comment paths

    const val PROJECT_ISSUE_COMMENTS = "/projects/{pid}/issues/{iid}/comments"
    const val PROJECT_ISSUE_COMMENT = "/projects/{pid}/issues/{iid}/comments/{cid}"

    // Label paths

    const val PROJECT_LABELS = "/projects/{pid}/labels"
    const val PROJECT_ISSUE_LABELS = "/projects/{pid}/issues/{iid}/labels"

    // State paths

    const val PROJECT_STATES = "/projects/{pid}/states"

    // Transition paths

    const val PROJECT_TRANSITIONS = "/projects/{pid}/transitions"
    const val PROJECT_TRANSITION = "/projects/{pid}/transitions/{tid}"

    // Project Member paths

    const val PROJECT_MEMBERS = "/projects/{pid}/members"

    fun forProject(pid: Int) = UriTemplate(PROJECT).expand(pid)
    fun forProjectName(pid: Int) = UriTemplate(PROJECT_NAME).expand(pid)
    fun forProjectDescription(pid: Int) = UriTemplate(PROJECT_DESCRIPTION).expand(pid)
    fun forProjectInitialState(pid: Int) = UriTemplate(PROJECT_INITIAL_STATE).expand(pid)
    fun forProjectIssues(pid: Int) = UriTemplate(PROJECT_ISSUES).expand(pid)
    fun forProjectLabels(pid: Int) = UriTemplate(PROJECT_LABELS).expand(pid)
    fun forProjectStates(pid: Int) = UriTemplate(PROJECT_STATES).expand(pid)
    fun forProjectTransitions(pid: Int) = UriTemplate(PROJECT_TRANSITIONS).expand(pid)
    fun forProjectMembers(pid: Int) = UriTemplate(PROJECT_MEMBERS).expand(pid)

    fun forIssue(pid: Int, iid: Int) = UriTemplate(PROJECT_ISSUE).expand(pid, iid)
    fun forIssueName(pid: Int, iid: Int) = UriTemplate(PROJECT_ISSUE_NAME).expand(pid, iid)
    fun forIssueDescription(pid: Int, iid: Int) = UriTemplate(PROJECT_ISSUE_DESCRIPTION).expand(pid, iid)
    fun forIssueState(pid: Int, iid: Int) = UriTemplate(PROJECT_ISSUE_STATE).expand(pid, iid)
    fun forIssueLabels(pid: Int, iid: Int) = UriTemplate(PROJECT_ISSUE_LABELS).expand(pid, iid)
    fun forIssueComments(pid: Int, iid: Int) = UriTemplate(PROJECT_ISSUE_COMMENTS).expand(pid, iid)

    fun forComment(pid: Int, iid: Int, cid: Int) = UriTemplate(PROJECT_ISSUE_COMMENT).expand(pid, iid, cid)

    fun forTransition(pid: Int, tid: Int) = UriTemplate(PROJECT_TRANSITION).expand(pid, tid)

}

object Hateos {

    object Classes {

        /* ** DEFAULT ** */

        const val SHORT = "short"

        /* ** Domain ** */

        const val PROJECTS = "/classes/projects"
        const val PROJECT = "/classes/project"

        const val ISSUES = "/classes/issues"
        const val ISSUE = "/classes/issue"

        const val COMMENTS = "/classes/comments"
        const val COMMENT = "/classes/comment"

        const val STATES = "/classes/states"
        const val STATE = "/classes/state"

        const val LABELS = "/classes/labels"
        const val LABEL = "/classes/label"

        const val TRANSITIONS = "/classes/transitions"
        const val TRANSITION = "/classes/transition"

        const val MEMBERS = "/classes/members"
        const val MEMBER = "/classes/member"
    }

    object Relations {

        /* ** DEFAULT ** */

        const val SELF = "self"
        const val COLLECTION = "collection"
        const val ITEM = "item"
        const val PARENT = "parent"

        /* ** Domain ** */

        const val PROJECTS = "/rels/projects"
        const val PROJECT = "/rels/project"

        const val ISSUES = "/rels/project/issues"
        const val ISSUE = "/rels/project/issue"

        const val COMMENTS = "/rels/project/issue/comments"

        const val STATES = "/rels/project/states"

        const val PROJECT_LABELS = "/rels/project/labels"
        const val ISSUE_LABELS = "/rels/project/issue/labels"

        const val TRANSITIONS = "/rels/project/transitions"

        const val MEMBERS = "/rels/project/members"

    }

    object Actions {

        const val CREATE_PROJECT = "/actions/create/project"
        const val CREATE_ISSUE = "/actions/create/issue"
        const val CREATE_TRANSITION = "/actions/create/transition"
        const val CREATE_COMMENT = "/actions/create/comment"

        const val DELETE_PROJECT = "/actions/delete/project"
        const val DELETE_TRANSITION = "/actions/delete/transition"
        const val DELETE_COMMENT = "/actions/delete/comment"
        const val DELETE_ISSUE = "/actions/delete/issue"

        const val UPDATE_PROJECT = "/actions/update/project"
        const val UPDATE_PROJECT_NAME = "/actions/update/project/name"
        const val UPDATE_PROJECT_DESCRIPTION = "/actions/update/project/description"
        const val UPDATE_PROJECT_INITIAL_STATE = "/actions/update/project/initialState"
        const val UPDATE_COMMENT = "/actions/update/comment"
        const val UPDATE_ISSUE = "/actions/update/issue"
        const val UPDATE_ISSUE_NAME = "/actions/update/issue/name"
        const val UPDATE_ISSUE_DESCRIPTION = "/actions/update/issue/description"
        const val UPDATE_ISSUE_STATE = "/actions/update/issue/state"

        const val ADD_PROJECT_LABEL = "/actions/add/project/label"
        const val ADD_PROJECT_STATE = "/actions/add/project/state"
        const val ADD_PROJECT_MEMBER = "/actions/add/project/member"
        const val ADD_ISSUE_LABEL = "/actions/add/issue/label"

        const val REMOVE_PROJECT_LABEL = "/actions/remove/project/label"
        const val REMOVE_PROJECT_STATE = "/actions/remove/project/state"
        const val REMOVE_PROJECT_MEMBER = "/actions/remove/project/member"
        const val REMOVE_ISSUE_LABEL = "/actions/remove/issue/label"
    }
}

object Auth {
    const val CLIENT_SECRET = "macaco"
    const val CLIENT_ID = "api"
    const val TOKEN_INTROSPECTION_ENDPOINT = "http://10.154.0.14/openid-connect-server-webapp/introspect"
}

@Configuration
class AppConfig(val authInterceptor: BearerAuthenticationInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor)
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(7, SirenMessageConverter())
        converters.add(7, HALMessageConverter())
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("http://35.197.250.227")
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.LOCATION)
    }
}

/*
@Configuration
@EnableSwagger2
class DocsConfig : WebMvcConfigurer {
    @Bean
    fun api() = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")

    }

}*/