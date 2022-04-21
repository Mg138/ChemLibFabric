import json
import pathlib

output = {
    "chemicals": [
    ]
}


def to_color(str: str):
    split = str.split(',')
    split = list(map(lambda s: s.strip(), split))

    return list(map(int, split))


def reformat_compound(compound):
    id = compound["name"]

    try:
        count = compound["count"]

        return {
            "id": id,
            "count": count,
        }
    except KeyError:
        return {
            "id": id
        }


with open('_compounds.json') as file:
    jsonfile = json.load(file)

    for compound in jsonfile["compounds"]:
        type = "compound"
        id = compound["name"]
        color = to_color(compound["color"])
        compounds = list(map(reformat_compound, compound["components"]))

        output["chemicals"].append(
            {
                "type": type,
                "id": id,
                "color": color,
                "compounds": compounds
            }
        )

output = json.dumps(output, indent=4)
output_file = pathlib.Path("compounds.json")
output_file.write_text(output)
