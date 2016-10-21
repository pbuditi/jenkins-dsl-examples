def views_json = readFileFromWorkspace("views.json")
def views = new groovy.json.JsonSlurper().parseText(views_json)["views"]

def tasks_json = readFileFromWorkspace("tasks.json")
def tasks = new groovy.json.JsonSlurper().parseText(tasks_json)["tasks"]

def env_json = readFileFromWorkspace("DEV.json")
def env = new groovy.json.JsonSlurper().parseText(env_json)


def gitCred = "c79906d0-32e2-41bd-bcef-5d447125a3eb"
def gitURL = "https://github.com/pbuditi/pradeep-apps.git"

views.each { view ->

	tasks.each { task -> 

		job("${view}-dsl-example-${task}") {
			scm {
				git {
					remote {
				 		url(gitURL)
				 		credentials(gitCred)
					}
				}
			}
			steps {
				shell("echo this is job ${task}")
				shell("echo this is job ${env.name}")
			}
		}
	}



	listView(view) {

		jobs {
			regex("${view}-.*")
		}
		columns {
			status()
			weather()
			name()
			lastSuccess()
			lastFailure()
			lastDuration()
			buildButton()
		}
	}	

}
