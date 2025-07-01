# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

FormalCalculation is a Scala 3.7.0 project that implements formal (symbolic) mathematical computation without relying on numerical approximation. The system provides exact arithmetic operations using algebraic manipulation and symbolic processing to avoid floating-point precision issues.

## Architecture

The project follows a multi-module structure with three main modules:

- **abstract-algebra**: Foundational algebraic structures (base module)
  - `Semigroup`: Types with associative binary operations
  - `Monoid`: Semigroups with identity elements  
  - Built-in instances for common types (String, Int, List, Boolean, Option)
  - Property-based testing for algebraic laws

- **arithmetic**: Core mathematical types and operations (depends on abstract-algebra)
  - `Natural`: Natural numbers (0+) implemented using binary representation with `List[Boolean]`
  - `Integer`: Signed integers using `Natural` with sign information  
  - Number theory utilities in `NumberTheory.scala`
  - Integer operations and utilities in `IntegerOps.scala`
  
- **function**: Higher-level functional abstractions (depends on arithmetic module)

### Key Design Principles

- **Immutable data structures**: All mathematical objects are immutable
- **Type safety**: Extensive use of sealed traits and case classes
- **Binary representation**: Natural numbers use `List[Boolean]` where the head is the least significant bit
- **Exact arithmetic**: No floating-point approximations - all calculations maintain mathematical precision

## Common Development Commands

### Building and Testing
```bash
# Compile all modules
sbt compile

# Run all tests
sbt test

# Run tests for specific module
sbt abstractAlgebra/test
sbt arithmetic/test
sbt function/test

# Run specific test class
sbt "testOnly *NaturalSpec"

# Clean build artifacts
sbt clean

# Interactive development mode (recompiles on file changes)
sbt ~compile
sbt ~test
```

### Code Quality
```bash
# Run scalafix (static analysis)
sbt scalafix

# Check for WartRemover violations (configured in build.sbt)
sbt compile  # WartRemover runs during compilation

# Format code (scalafmt is available via plugin)
sbt scalafmt
```

### Project Navigation
```bash
# List all projects
sbt projects

# Switch to specific project
sbt project abstractAlgebra
sbt project arithmetic
sbt project function
```

## Testing Framework

The project uses **ScalaTest** with the FlatSpec style, along with **ScalaCheck** for property-based testing. Test files follow the pattern `*Spec.scala` and are located in `src/test/scala/` within each module.

Example test structure:
```scala
class NaturalSpec extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks {
  "Natural.fromInt" should "create correct natural numbers" in {
    // test implementation
  }
}
```

## Key Implementation Details

### Natural Number Representation
- Zero: `Natural.Zero` (empty bit list)
- Positive numbers: `Natural.Positive(bits: List[Boolean])` where `bits.head` is LSB
- All operations maintain the invariant that the MSB is always `true` for positive numbers

### Integer Implementation  
- Uses `Natural` internally with separate sign tracking
- Three cases: `Zero`, `Positive(Natural)`, `Negative(Natural)`
- Division returns `Option[(Integer, Integer)]` for quotient and remainder

### Build Configuration
- **Scala version**: 3.7.0
- **Key plugins**: sbt-tpolecat, sbt-scalafmt, sbt-scalafix, sbt-wartremover
- **WartRemover rules**: Extensive static analysis rules configured in `build.sbt`
- **Dependencies**: Cats, ScalaTest, ScalaCheck, SLF4J logging

## Project Structure Navigation

```
abstract-algebra/src/main/scala/com/formalcalculation/algebra/
├── Semigroup.scala                    # Semigroup trait and instances
├── Monoid.scala                       # Monoid trait and basic instances
├── Instances.scala                    # Helper for creating instances
└── instances/
    ├── package.scala                  # Instance exports
    └── ArithmeticInstances.scala      # Forward declarations for arithmetic types

abstract-algebra/src/test/scala/com/formalcalculation/algebra/
├── SimpleMonoidSpec.scala             # Property-based monoid law tests
└── SimpleSemigroupSpec.scala          # Property-based semigroup law tests

arithmetic/src/main/scala/com/formalcalculation/arithmetic/
├── Natural.scala                      # Natural number implementation
├── Integer.scala                      # Integer implementation  
├── IntegerOps.scala                   # Integer utility operations
└── NumberTheory.scala                 # Number theory functions

arithmetic/src/test/scala/com/formalcalculation/arithmetic/
├── NaturalSpec.scala
├── IntegerSpec.scala
├── IntegerOpsSpec.scala  
└── NumberTheorySpec.scala
```

The project follows functional programming principles with immutable data structures and strong type safety. All mathematical operations are implemented from first principles using exact binary arithmetic.