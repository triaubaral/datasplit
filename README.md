# datasplit

Hypothèses de travail :
        - le mode lot = appel séquentiel d'un webservice avec en paramètre un fichier xml contenant une quantité limité d'enregistrements à traiter à la fois.
        - le fichier xml en paramètre a été généré après avoir traité un ensemble d'enregistrements hétérogènes.
        - le fichier xml en paramètre doit contenir une certaine quantité d'enregistrements homogènes.

    Problématique :
        - Comment classer de manière fluide en ensemble d'enregistrements hétérogènes afin de générer des fichiers xml contenant des enregistrements homogènes qui serviront de paramètre pour le lancement du mode lot ?

    Proposition de solution :

    - Chaque enregistrement du fichier en paramètre doit avoir un caractère discriminant qui lui permettra par la suite d'être regroupé avec des enregistrements partageant le même discriminant.
    Par exemple (cf projet en pj) :
    Si on a la liste de fruits suivante : fraise, framboise, pomme, poire, banane, pamplemouse. On peut dire que le caractère discriminant est la couleur. Ainsi on aurait 3 fichiers xml ayant deux enregistrements par fichier xml : [rouge(fraise,framboise)], [vert(pomme, poire)], [jaune[banane, pamplemouse)].

    - En plus d'un critère discriminant, il faut pouvoir indiquer le nombre d'enregistrements par fichier. Par exemple, on pourrait dire que pour les fruits rouge on souhaite n'avoir qu'un enregistrement par fichier. En résultat on aurait 4 fichiers xml : [rouge(framboise)], [rouge(framboise)], [vert(pomme, poire)], [jaune[banane, pamplemouse)].

    Proposition d'implémentation cf projet pour plus d'infos :

    Dans notre exemple cela donnerait en résumé ceci :
        - on créé une classe Fruit qui implémente le comportement Sortable qui impose de définir un caractère discriminant ici la couleur.
        - on créé un classe Discriminant qui implémente le comportant Groupable qui impose de définir une quantité de regroupement associé au discriminant que l'on a choisit d'implémenter.
        - on fait appel à la méthode dispatch pour réaliser un premier classement par critère, mais sans prise en compte de la contrainte quantité, de la liste de fruits que l'on passe en paramètre :
            {Discriminant [criteria=jaune, tailleDuLot=2]=[Fruit [name=banane], Fruit [name=pamplemouse]],
             Discriminant [criteria=vert, tailleDuLot=2]=[Fruit [name=pomme], Fruit [name=poire]],                     
             Discriminant [criteria=rouge, tailleDuLot=1]=[Fruit [name=fraise], Fruit [name=framboise]]}

        - on fait appel ensuite à la méthode dispatchByTailleDuLot pour appliquer sur la liste obtenue précédemment afin d'avoir une représentation mémoire d' enregistrements homogènes avec prise en compte de la contrainte quantité (cf crochet en gras qui font la différence avec la méthode dispatch) !
            {Discriminant [criteria=jaune, tailleDuLot=2]=[[Fruit [name=banane], Fruit [name=pamplemouse]]],
             Discriminant [criteria=vert, tailleDuLot=2]=[[Fruit [name=pomme], Fruit [name=poire]]],
             Discriminant [criteria=rouge, tailleDuLot=1]=[[Fruit [name=fraise]], [Fruit [name=framboise]]]}

        - Une fois que l'on la structure précédente il suffit de lire le tableau de tableau obtenu et de créer de manière fluide l'ensemble des fichiers xml nécessaires.