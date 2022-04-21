import csv
import json
import pathlib

output = {
    "chemicals": [
    ]
}

with open('_elements.csv') as csvfile:
    reader = csv.reader(csvfile)

    for row in reader:
        type = "element"
        id = row[1]
        color = [
            int(row[3]),
            int(row[4]),
            int(row[5])
        ]
        name = row[2]
        atomic_number = int(row[0])
        ingot = row[6] == "true"
        output["chemicals"].append(
            {
                "type": type,
                "id": id,
                "color": color,
                "name": name,
                "atomic_number": atomic_number,
                "ingot": ingot,
            }
        )

output = json.dumps(output, indent=4)
output_file = pathlib.Path("elements.json")
output_file.write_text(output)
