import util.Common
Common.makeFolders(this)

def folder = 'release'

pipelineJob("${folder}/nightly-release") {
  description('Tag and release the DM pipelines/dax "nightly".')

  parameters {
    stringParam('YEAR', null, 'Gregorian calendar year.')
    stringParam('MONTH', null, 'Gregorian calendar month.')
    stringParam('DAY', null, 'Gregorian day of calendar month.')
  }

  properties {
    rebuild {
      autoRebuild()
    }
  }

  // don't tie up a beefy build slave
  label('jenkins-master')
  concurrentBuild(false)
  keepDependencies(true)

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
      scriptPath("pipelines/${folder}/nightly_release.groovy")
    }
  }
}