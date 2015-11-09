
Closure<String> closureEngine = {
    "Engine installation"
}

def closureCarBody = {
    "CarBody assembly"
}

def closureDoor = { int numberOfDoors = 1 ->
    def ss = numberOfDoors > 1 ? 's' : ''
    "$numberOfDoors Door$ss installation"
}

def closureWheel = { numberOfWheels = 1 ->
    def ss = numberOfWheels > 1 ? 's' : ''
    "$numberOfWheels Wheel$ss mounting"
}

String baseDir = '.'
def conveyorMap = [Engine: closureEngine, CarBody: closureCarBody, Door: closureDoor, Wheel: closureWheel]

def fileMap = [:]
def analyzeFile = { File f ->
    f.eachLine { line ->
        if (line.contains('=')) {
            String[] m = line.split('=')
            fileMap.put(m[0].trim(), m[1].trim())
        } else {
            fileMap.put(line.trim(), null)
        }
        return fileMap
    }
}


def listOut = []
File file = new File(baseDir, 'conveyor.txt')

analyzeFile(file).each { key, value ->
    if (conveyorMap.containsKey(key)) {
        def productionArea
        if (value) {
            productionArea = conveyorMap.get(key).call(value as Integer)
        } else {
            productionArea = conveyorMap.get(key).call()
        }
        listOut << productionArea
    }
}

println listOut
