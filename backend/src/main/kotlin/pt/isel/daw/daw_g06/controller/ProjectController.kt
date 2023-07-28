package pt.isel.daw.daw_g06.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.daw_g06.configs.Uri
import pt.isel.daw.daw_g06.controller.representation.input.DescriptionInputModel
import pt.isel.daw.daw_g06.controller.representation.input.NameInputModel
import pt.isel.daw.daw_g06.controller.representation.input.ProjectInputModel
import pt.isel.daw.daw_g06.controller.representation.input.StateInputModel
import pt.isel.daw.daw_g06.controller.representation.output.ProjectsOutputModel
import pt.isel.daw.daw_g06.controller.representation.output.toOutputModel
import pt.isel.daw.daw_g06.controller.util.HateoasMediaType
import pt.isel.daw.daw_g06.service.contract.IProjectService

@RestController
class ProjectController(private val projectService: IProjectService) {

    @PostMapping(path = [Uri.PROJECTS], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createProject(
            @RequestAttribute("uid") uid: Int,
            @RequestBody body: ProjectInputModel
    ): ResponseEntity<Unit> {
        val pid = projectService.create(uid, body.mapToProject())
        return ResponseEntity
                .created(Uri.forProject(pid))
                .build()
    }

    @GetMapping(
            path = [Uri.PROJECTS],
            produces = [
                MediaType.APPLICATION_JSON_VALUE,
                HateoasMediaType.APPLICATION_JSON_SIREN_VALUE,
                HateoasMediaType.APPLICATION_JSON_HAL_VALUE
            ]
    )
    fun getProjects(): ProjectsOutputModel {
        val projects = projectService.readProjects().map { it.toOutputModel() }
        return ProjectsOutputModel(projects.size, projects)
    }

    @GetMapping(
            path = [Uri.PROJECT],
            produces = [
                MediaType.APPLICATION_JSON_VALUE,
                HateoasMediaType.APPLICATION_JSON_SIREN_VALUE,
                HateoasMediaType.APPLICATION_JSON_HAL_VALUE
            ]
    )
    fun getProject(
            @PathVariable("pid") pid: Int
    ) = projectService.readProject(pid).toOutputModel()


    @PutMapping(path = [Uri.PROJECT], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateProject(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @RequestBody body: ProjectInputModel
    ): ResponseEntity<Unit> {
        projectService.updateProject(uid, body.mapToProject(pid))
        return ResponseEntity.noContent().build()
    }

    @PatchMapping(path = [Uri.PROJECT_NAME], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateProjectName(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @RequestBody body: NameInputModel
    ): ResponseEntity<Unit> {
        projectService.updateProjectName(uid, pid, body.name)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping(path = [Uri.PROJECT_DESCRIPTION], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateProjectDescription(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @RequestBody body: DescriptionInputModel
    ): ResponseEntity<Unit> {
        projectService.updateProjectDescription(uid, pid, body.description)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping(path = [Uri.PROJECT_INITIAL_STATE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateProjectInitialState(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int,
            @RequestBody body: StateInputModel
    ): ResponseEntity<Unit> {
        projectService.updateProjectInitialState(uid, pid, body.name)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping(path = [Uri.PROJECT])
    fun deleteProject(
            @RequestAttribute("uid") uid: Int,
            @PathVariable("pid") pid: Int
    ): ResponseEntity<Unit> {
        projectService.deleteProject(uid, pid)
        return ResponseEntity.noContent().build()
    }
}