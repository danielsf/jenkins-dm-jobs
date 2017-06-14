import util.Common
Common.makeFolders(this)

def folder = 'sqre/ci-ci'

pipelineJob("${folder}/test-pipeline-docker") {
  description('Test running a build inside a docker container.')

  properties {
    rebuild {
      autoRebuild()
    }
  }

  label('jenkins-master')
  keepDependencies(true)
  concurrentBuild()

  def repo = SEED_JOB.scm.userRemoteConfigs.get(0).getUrl()
  def ref  = SEED_JOB.scm.getBranches().get(0).getName()

  definition {
    cpsScm {
      scm {
        git {
          remote {
            url(repo)
          }
          branch(ref)
        }
      }
      scriptPath("pipelines/${folder}/test_pipeline_docker.groovy")
    }
  }
}
