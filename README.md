# The Computon Model
## Status: Under Development — Not Ready for Production

This framework is designed to enable the construction of complex, high-level computing devices. It was implemented according to the categorical semantics of the computon model, originally presented in [1](https://arxiv.org/pdf/2309.06397) and subsequently extended in [2](https://arxiv.org/pdf/2506.22687) and [3](). The [Computon Project's](https://damianarellanes.github.io/computons-website/) website provides a high-level overview of the model, including current applications and relevant background literature. 

1. Damian Arellanes. [Compositional Separation of Control Flow and Data Flow](https://arxiv.org/pdf/2309.06397). Journal of Logical and Algebraic Methods in Programming, 2026. [DOI](https://doi.org/10.1016/j.jlamp.2026.101125)
2. Damian Arellanes. [Compositional Control-Driven Boolean Circuits](https://arxiv.org/pdf/2506.22687). In Proceedings of the 22nd International Conference on Relational and Algebraic Methods in Computer Science (RAMICS), 2026. [DOI](https://doi.org/10.1007/978-3-032-22469-9_2)
3. Damian Arellanes. [Colimit-Based Composition of High-Level Computing Devices](https://arxiv.org/abs/2602.14904v1). In 21st International Symposium on Logical and Semantic Frameworks with Applications (LSFA), affiliated to the 11th International Conference on Formal structures for Computation and Deduction (FSCD) and part of the 9th Federated Logic Conference (FLoC), 2026. [DOI](https://cgi.cse.unsw.edu.au/~eptcs/paper.cgi?LFSA2026.3) 


## ⚠️ Development Status

This java-based framework is currently under active development and is not yet ready for production use.

- Features may be incomplete or unstable.
- APIs and internal implementations may change.
- Documentation is a work in progress.
- Breaking changes may occur without notice.
- Use it at your own discretion while development continues.

## ✨ Features

- Support for the systematic construction of atomic, primitive, trivial and composite computons.
- Composite computons can be formed through colimit-based operators that define asynchronous parallel (PAR), total sequential (TOTAL_SEQ), partial sequential (PARTIAL_SEQ), branching (BRAN), head-iterative (H_IT) and tail-iterative (T_IT) computational structures.
- Functionality for the execution of computons based on well-defined operational semantics.
- Support for the visualisation of computons.

More features will be documented as development progresses.

## 🚀 Getting Started

Usage examples are provided in the [Computons-Demo](https://github.com/damianarellanes/computons-demo) project. Additional use cases will be provided as the framework becomes more stable.

## 🛠️ Development

Development is currently taking place on the development branch.

Contributions, suggestions, and feedback are welcome.

## 📄 License

This project is licensed under the BSD 3-Clause License. See the LICENSE file for details.

## 🖋️ Attribution

If you use this framework in your projects, please consider crediting the author and linking back to this repository. 