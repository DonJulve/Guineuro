import React, { useState, useEffect } from "react";

const TextoIA = (id, dataType) => {
    //650
    const prog = { 0: "proj"};
    //345
    const inicio = {
        6: "El diagnóstico inicial de Victor nos indica que es alguien que presente ningun alto riesgo, pero si que tiene un riesgo en particular en cuanto a los síntomas negativos, se refiere, y requerira atención en ese ámbito, con tratamientos que ayuden al mantenimiento de una rutina y hábitos, junto con estimulación de las habilidades cognitivas.",
        7: "El diagnóstico inicial de Juaquon nos inidca un mayor riesgo que la média, teniendo en particular mas agravados los delirios, las alucinaciones y los sintomas negativos, requerira la medicación pertinente para prevenir el desarrollo de psicosis o paranoia, también requerira un seguimiento mas de cerca para asegurar un desarrollo favorable",
        1: "El diagnóstico inicial de Juana presenta un riesgo moderado, sin embargo no hay ningún ambito en el que se pueda decir que destaque por encima de la media, por lo que se tendrá que descartar hacer un tratamiento mas preciso, y tratar de aplicar uno mas general para las condiciones que presenta, aunque pueda ser menos efectivo en cada area.",
    };
    //132, solo de victor
    const day = [
        "En este día se ha de destacar que Victor presenta unos sintomas negativos altamente irregulares, junto un habla incoherente al alza.",
        "En este día se ha de destacar que Victor presenta un empeoramiento generalizado de sus sintomas, destacando las alucinaciones.",
        "En este día se ha de destacar que Victor presenta una mejora generalizada, particularmente en el habla y movimientos nerviosos.",
        "En este día se ha de destacar que Victor presenta una mejora generalizada, particularmente en el habla y movimientos nerviosos."
    ];

    const [selectedText, setSelectedText] = useState("");

    const handleSelectById = (id, dataType) => {
        let selectedText = "";
    
        // Lógica para seleccionar el texto según el tipo de datos
        if (dataType === "prog") {
          selectedText = prog[id] || "ID no encontrado";
        } else if (dataType === "inicio") {
          selectedText = inicio[id] || "ID no encontrado";
        } else if (dataType === "day") {
          selectedText = day[id - 1] || "ID no encontrado"; // Asumiendo que day es un array
        } else {
          selectedText = "Tipo de datos no válido";
        }
        setSelectedText(selectedText);
    }

    useEffect(() => {
        handleSelectById(id, dataType);
      }, [id, dataType]);

      const str = dataType.toString();
      console.log(str);


    return (
        <p>{selectedText} y </p>
    )

    
}

export default TextoIA;


